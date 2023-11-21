// import 'package:flame/game.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
// import 'package:robot_worlds_app/client_control.dart';
import 'package:robot_worlds_app/gamescreens/world_screen.dart';
import 'package:robot_worlds_app/screens/server_select_screen.dart';
// import 'package:robot_worlds_app/player/joystick_screen.dart';
import 'package:robot_worlds_app/screens/worlds_list_screen.dart';

import 'controllers/world_controller.dart';
// import 'package:robot_worlds_app/worlds_list.dart';
// import 'server_select.dart';

void main() {
  runApp(ChangeNotifierProvider(
      create: (context) => WorldControl(),
      child: const MyApp(),
    ),);
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Robot Worlds',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: const ServerSelect(),
    );
  }
}