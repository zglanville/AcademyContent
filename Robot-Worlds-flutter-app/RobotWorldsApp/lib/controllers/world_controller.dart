import 'dart:core';
import 'package:flutter/material.dart';
import '../controllers/client_control.dart';
import '../models/worlds_model.dart';
import '../models/obstacle_model.dart';
import '../models/robot_model.dart';

class WorldControl with ChangeNotifier {
  WorldsModel world = WorldsModel();
  ClientControl client = ClientControl();

  // void getRobots() async {
  //   var response = await client.getRequest('/')
  // }

  void getObstacles() async {
    var response = await client.getRequest('/world');
    var ele = response["obstacles"];
    print(response.toString());
    List<Obstacle> obs = [];

    // JSONresponse = jsonDecode(response.body);
    ele.forEach((element) {
      var ob = Obstacle.fromJson(element);
      obs.add(ob);
    });
    world.obstacles = obs;
  }

  List<Obstacle> getWorldObstacles() {
    // createObstacle();
    getObstacles();
    return world.obstacles;
  }

  Future<String> createObstacle(String x, String y) async {
    List<Map<String, String>> obs = [];
    obs.add(<String, String>{
      'x': x,
      'y': y,
    });
    // obs.add(<String, String>{
    //   'x': '1',
    //   'y': '1',
    // });

    var response = await client.postRequest('/admin/obstacles', obs);
    return response.toString();
  }

  Future<String> deleteObstacle(String x, String y) async {
    List<Map<String, String>> obs = [];
    obs.add(<String, String>{
      'x': x,
      'y': y,
    });
    // obs.add(<String, String>{
    //   'x': '1',
    //   'y': '1',
    // });

    var response = await client.deleteObsRequest('/admin/obstacles', obs);
    return response.toString();
  }

  Future<List<Robot>> getBots() async {
    var response = await client.getRequest('/admin/robots');
    List<Robot> bots = [];
    response.forEach((element) {
      var bot = Robot.fromJson(element);
      bots.add(bot);
    });
    world.robots = bots;
    return bots;
  }

  Future<List<String>> getBotNames() async {
    var bots = await getBots();
    List<String> robotNames = [];
    for (var element in bots) {
      robotNames.add(element.name + "\n");
    }
    // world.robotNames = robotNames;
    return robotNames;
  }

  Future<Object> deleteBot(String robotName) async {
    // Object obj = {};
    //List obj = ["delete"];
    var response = await client.deleteRequest('/admin/robot/' + robotName);
    return response;
  }

  // String getWorldBots() {
  //   List<String> names = [];
  //   getBotNames().then((List<String> botNames) => names = botNames);
  //   return names.toString();
  // }

  Future<void> getWorlds() async {
    var response = await client.getRequest('/worlds');
    world.worlds = response.cast<String>();
    notifyListeners();
  }

  Future<String> loadSavedWorld(String worldName) async {
    await client.getRequest('/admin/load/$worldName');
    notifyListeners();
    return worldName;
  }

  Future<String> loadWorld(String worldName) async {
    var response = await client.getRequest('/admin/load/' + worldName);
    return response.toString();
  }

  Future<String> saveWorld(String worldName) async {
    var response = await client.postWorldRequest('/admin/save/' + worldName);
    return response.toString();
  }

  Future<String> commandRobot(
      String robotName, String command, List<String> arguments) async {
    var bodyString = <String, String>{
      'robot': robotName,
      'command': command,
    };
    var bodyList = <String, List<String>>{
      'arguments': arguments,
    };

    var body = {...bodyString, ...bodyList};
    var response = await client.postRequest('/robot/' + robotName, body);
    return response.toString();
  }

  Future<int> serverRequest(String ip, String port) async {
    var response = await client.getPing('/world');
    return response;
  }

  /*Future<void> getWorldDetails() async {
    client.getRequest('/world').then((json) {
      name = json["name"] as String;
      height = json['height'] as int;
      width = json['width'] as int;
      config = json['config'] as Map;
      obstacles = json['obstacles'] as List;
      mines = json['mines'] as List;
      pits = json['pits'] as List;
      edges = json['edges'] as List;
      openSpaces = parseCoordinatesList(json['openSpaces']);

      id = json['id'] as int;
      notifyListeners();
    });
  }

  static List<List<int>> parseCoordinatesList(List responseBody) {
    List<List<int>> newMap = [];
    for (String e in responseBody) {
      List<int> position = [];

      var tempList = e
          .replaceAll('[', '')
          .replaceAll(']', '')
          .replaceAll(' ', '')
          .split(',');
      position.add(int.parse(tempList[0]));
      position.add(int.parse(tempList[1]));

      newMap.add(position);
    }
    return newMap;
  }*/
}
