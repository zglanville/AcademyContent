import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:robot_worlds_app/screens/admin_button_screen.dart';
import 'package:robot_worlds_app/screens/launch_robot_screen.dart';
import 'package:robot_worlds_app/widgets/reusable_elevated_button.dart';
import 'package:robot_worlds_app/widgets/reusable_scaffold.dart';
import 'package:robot_worlds_app/widgets/reusable_textbox.dart';

import 'admin_button_screen.dart';
import 'launch_robot_screen.dart';

class Selection extends StatefulWidget {
  const Selection({Key? key}) : super(key: key);

  @override
  _SelectionState createState() => _SelectionState();
}

class _SelectionState extends State<Selection> {
  @override
  Widget build(BuildContext context) {
    return CustomScaffold(appBarText: 'Robot Worlds', children: <Widget>[
      const SizedBox(height: 30),
      const CustomTextBox(
        boxText: "Select Player or Admin",
        height: 100,
      ),
      CustomElevatedButton(
          buttonText: 'Player',
          primary: Colors.blue,
          onPressed: () {
            Navigator.push(context,
                MaterialPageRoute(builder: (context) => const Launch()));
          }),
      const SizedBox(height: 20),
      CustomElevatedButton(
          buttonText: 'Admin',
          primary: Colors.blue,
          onPressed: () {
            Navigator.push(context,
                MaterialPageRoute(builder: (context) => const AdminPage()));
          }),
      const SizedBox(height: 90),
    ]);
  }
}
