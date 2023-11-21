class Robot {
  final String name;
  late String direction;
  late String status;
  late var position;

  Robot(
      {required this.name,
      required this.direction,
      required this.position,
      required this.status});

  factory Robot.fromJson(Map<String, dynamic> json) {
    return Robot(
      name: json['name'],
      position: json['position'],
      direction: json['currentDirection'],
      status: json['robotStatus'],
    );
  }

  // void setDirection(String newDirection) {
  //   direction = newDirection;
  // }

  // void setStatus(String newStatus) {
  //   status = newStatus;
  // }

  // void setPosition(List<int> newPosition) {
  //   position = newPosition;
  // }

  void updateRobot(){
    
  }
}
