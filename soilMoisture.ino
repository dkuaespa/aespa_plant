void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  //mySerial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  int soildMoisture = analogRead(A0);
  Serial.println(soildMoisture);
  delay(1000);
}
