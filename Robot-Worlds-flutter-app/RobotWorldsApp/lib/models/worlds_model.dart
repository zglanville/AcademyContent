import 'obstacle_model.dart';
import 'robot_model.dart';

class WorldsModel {
  // List<Obstacle> obstacles = [];
  List<String> worlds = <String>[];
  late int worldSize;
  List<Robot> robots = [];
  List<Obstacle> obstacles = [];
  List<String> robotNames = [];
  Robot myBot =
      Robot(name: 'null', direction: 'null', position: 'null', status: 'null');

  void updateBot(Map<String, dynamic> json) {
    myBot = Robot.fromJson(json);
  }
}



/*  String name = '';
  int width = 0;
  int height = 0;
  Map config = {};
  List obstacles = [];
  List mines = [];
  List pits = [];
  List edges = [];
  List<List<int>> openSpaces = [];
  int id = 0;
  List<Robot> robots = [];*/