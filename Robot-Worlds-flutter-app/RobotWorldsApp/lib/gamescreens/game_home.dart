import 'dart:async';
import 'dart:js';
import 'package:flame/game.dart';
import 'package:flutter/material.dart';
// import 'package:flame/util.dart';
import 'package:flutter/services.dart';
import 'dart:ui';
import 'package:flame/flame.dart';
import 'obstacle_component.dart';

class RobotGame extends FlameGame with HasCollidables{
  @override
  Future<void> onLoad() async {
    unawaited(add(RocketComponent(position: size / 2, size: Vector2.all(20))));

    return super.onLoad();
  }
  // void render(Canvas canvas) {
  //   // TODO: implement render
  // }

  // void update(double t) {
  //   // TODO: implement update
  // }
}
