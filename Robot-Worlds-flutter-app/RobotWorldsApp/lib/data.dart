class Data {
  static late String _ip;
  static late String _port;

  static String get ip => _ip;

  static String get port => _port;

  static set port(String value) {
    _port = value;
  }

  static set ip(String value) {
    _ip = value;
  }
}