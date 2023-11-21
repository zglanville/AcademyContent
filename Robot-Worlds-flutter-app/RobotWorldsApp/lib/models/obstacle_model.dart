class Obstacle {
  final int x;
  final int y;

  Obstacle({required this.x, required this.y});

  factory Obstacle.fromJson(Map<String, dynamic> json) {
    return Obstacle(
      x: json['bottomLeftX'] * 100,
      y: json['bottomLeftY'] * 100,
    );
  }
}