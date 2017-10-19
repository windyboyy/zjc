String inputString ="";         // 缓存字符串
boolean stringComplete = false;  // 是否string已经完成缓存
boolean isWork = false;//标志cantool虚拟装置是否在运行
String msg_rtn = "";    //共用返回字符串
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);//cantoolApp要求的频率
 // 初始化串口:
  // 将inputString反转200个字符:
  Serial.println("hello");
  pinMode(2,INPUT);                 //将2号数字口设置为输入状态，13号数字口设置为输出状态
                                    //(板上连接GND__D2两个点模拟总线发送标准帧和扩展帧的事件)
  digitalWrite(2,HIGH);
  inputString.reserve(200);
}

void loop() {
  // put your main code here, to run repeatedly:
 // 如果缓存string接收完成:
  if (stringComplete) {
   // serial_return(inputString);
    chooseFunction(inputString);
    //Serial.println(inputString.length());
    // 清空String:
    inputString ="";
    stringComplete = false;
    
  }
 
  /******这部分就是希望使用板子上的电平变化来模拟总线输入信息
          需要考虑标准帧和扩展帧两种情况需要两个数字口的变化(都与GND)           待写
  ******/
  int n =digitalRead(2);                   
    //创建一个变量n，将2号数字口的状态采集出来赋值给他。
  if (n==LOW)                             //判断n是否为高电平，如果是执行下面的语句，不是则跳过。
  {

    delay(1000);
    Serial.println("something happened");
    digitalWrite(2,HIGH);
}
/****************************************************/


}

/*
  SerialEvent在arduino板上的RX引脚收到数据时会被系统自动调用。
  在系统内部，它是在每次loop函数执行时连带执行的。因此如果再loop使用delay,serialEvent的调用也会被延迟，
  这样就有可能一次收到>=2个字符。
 */
void serialEvent() {
  while (Serial.available()) {
    // 获取新的字符:
    char inChar = (char)Serial.read();
    // Serial.println(inChar);
    // 将它加到inputString中:
    inputString += inChar;
    //在cantool工具中,指令都是使用"\r"作为结尾,同时,如果收到了回车符，
    //就将一个“旗标”变量设置为true，这样loop函数就知道inputString已经缓存完成了:
    if (inChar == '\r') {
      stringComplete = true;
    }
  }
}

//对从cantoolapp传入的信息的处理,判断程序需要调用哪个功能的函数
void chooseFunction(String message){
  
    char order = message[0];
    if(order != 'O'){
      if(isWork == true){
        switch(order){
        case 'V':
        if(message[1]=='\r')
        version_return();
        break;
        case 'C':
        if(message[1]=='\r')
        close_cantool();
        break;
        case 'S':
        if(message[1]=='n'){
          serial_return();
          }
        break;
        }
        }
      }else if(order == 'O'){
        open_cantool();
        }
   
    
    
 }
 
//处理板子上的电平变化,一个标准帧发送,一个扩展帧发送  或者拆开写两个函数在loop里直接调用
void sendFrame(String m){
  }
  
//发送字符串函数(通用) 这里要考虑串口通讯使用一个一个字符传输,时刻牢记字符串最后有个\r
void serial_return(String message){
  
 
  int msg_length = message.length();
//  message.ToCharArray();
  for(int i=0;i<msg_length;i++){
     Serial.println(message[i]);
    }
   // Serial.println(msg_length);
  }
  
//返回cantool装置信息
void version_return(){
  //serial_return();
  msg_rtn = "SV2.5-HV2.0\r";
  serial_return(msg_rtn);
  }
//打开装置,开始工作 isWork作为flag表示cantool装置是否在运行  
void open_cantool(){
  if(isWork == false)
  isWork = true;
  msg_rtn = "\r";
  serial_return(msg_rtn);
  //返回\r
  }
//关闭装置 
void close_cantool(){
  if(isWork == true)
  isWork = false;
  msg_rtn = "\r";
  serial_return(msg_rtn);
  }
//Sn 改变通信速率 ,这里在考虑要不要结合硬件做一做总线的改变但是苦于板子上没有灯
void change_rate(){
  msg_rtn = "\r";
  serial_return(msg_rtn);
  }







  
