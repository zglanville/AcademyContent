import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:robot_worlds_app/controllers/world_controller.dart';
import 'package:robot_worlds_app/widgets/reusable_appbar.dart';
import 'package:robot_worlds_app/widgets/reusable_elevated_button.dart';
import 'package:robot_worlds_app/widgets/reusable_textfield.dart';

class AdminPage extends StatefulWidget {
  const AdminPage({Key? key}) : super(key: key);

  @override
  _AdminPageState createState() => _AdminPageState();
}

class _AdminPageState extends State<AdminPage> {
  final worldNameController = TextEditingController();
  final obstacleXController = TextEditingController();
  final obstacleYController = TextEditingController();

  final robotNameController = TextEditingController();

  @override
  void dispose() {
    worldNameController.dispose();
    obstacleXController.dispose();
    obstacleYController.dispose();
    robotNameController.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: const CustomAppBar(
          appBarText: 'Admin Commands',
        ),
        body: Padding(
            padding: const EdgeInsets.all(20.0),
            child: Row(children: <Widget>[
              Expanded(
                  child: Scaffold(
                      resizeToAvoidBottomInset: false,
                      body: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: <Widget>[
                            CustomTextField(
                              controller: robotNameController,
                              hintText: 'Robot name: ',
                            ),
                            CustomTextField(
                              controller: obstacleXController,
                              hintText: 'Obstacle x: ',
                            ),
                            CustomTextField(
                              controller: obstacleYController,
                              hintText: 'Obstacle y: ',
                            ),
                            CustomTextField(
                              controller: worldNameController,
                              hintText: 'World name: ',
                            ),
                          ]))),
              Expanded(
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      mainAxisSize: MainAxisSize.min,
                      children: <Widget>[
                    const SizedBox(height: 15),
                    CustomElevatedButton(
                        buttonText: 'Get All Bots',
                        primary: Colors.blue,
                        onPressed: () {
                          Provider.of<WorldControl>(context, listen: false)
                              .getBotNames()
                              .then((List<String> value) => showDialog(
                                  context: context,
                                  builder: (context) {
                                    return AlertDialog(
                                      content: Text(value.toString()),
                                    );
                                  }));
                        }),
                    const SizedBox(height: 20),
                    CustomElevatedButton(
                        buttonText: 'Delete A Bot',
                        primary: Colors.blue,
                        onPressed: () {
                          Provider.of<WorldControl>(context, listen: false)
                              .deleteBot(robotNameController.text)
                              .then((Object value) => showDialog(
                                  context: context,
                                  builder: (context) {
                                    return AlertDialog(
                                      content: Text(value.toString()),
                                    );
                                  }));
                        }),
                    const SizedBox(height: 20),
                    CustomElevatedButton(
                        buttonText: 'Create Obs',
                        primary: Colors.blue,
                        onPressed: () {
                          Provider.of<WorldControl>(context, listen: false)
                              .createObstacle(obstacleXController.text,
                                  obstacleYController.text)
                              .then((Object value) => showDialog(
                                  context: context,
                                  builder: (context) {
                                    return AlertDialog(
                                      content: Text(value.toString()),
                                    );
                                  }));
                        }),
                    const SizedBox(height: 20),
                    CustomElevatedButton(
                        buttonText: 'Delete Obs',
                        primary: Colors.blue,
                        onPressed: () {
                          Provider.of<WorldControl>(context, listen: false)
                              .deleteObstacle(obstacleXController.text,
                                  obstacleYController.text)
                              .then((Object value) => showDialog(
                                  context: context,
                                  builder: (context) {
                                    return AlertDialog(
                                      content: Text(value.toString()),
                                    );
                                  }));
                        }),
                    const SizedBox(height: 20),
                    CustomElevatedButton(
                        buttonText: 'Save World',
                        primary: Colors.blue,
                        onPressed: () {
                          Provider.of<WorldControl>(context, listen: false)
                              .saveWorld(worldNameController.text)
                              .then((Object value) => showDialog(
                                  context: context,
                                  builder: (context) {
                                    return AlertDialog(
                                      content: Text(
                                          'Saved world: ' + value.toString()),
                                    );
                                  }));
                        }),
                    const SizedBox(height: 20),
                    CustomElevatedButton(
                        buttonText: 'Load World',
                        primary: Colors.blue,
                        onPressed: () {
                          Provider.of<WorldControl>(context, listen: false)
                              .loadWorld(worldNameController.text)
                              .then((Object value) => showDialog(
                                  context: context,
                                  builder: (context) {
                                    return AlertDialog(
                                      content: Text(
                                          'Loaded world: ' + value.toString()),
                                    );
                                  }));
                        }),
                    const SizedBox(height: 40),
                  ]))
            ])));
  }
}

// Navigator.push(
//     context,
//     MaterialPageRoute(
//         builder: (context) => const Launch()));
// showDialog(
//   context: context,
//   builder: (context) {
//     return AlertDialog(
//       content: Text("OK"),
//     );
//   },
// );

// Expanded(
//   child: Column(
//     crossAxisAlignment: CrossAxisAlignment.center,
//     mainAxisSize: MainAxisSize.min,
//     children: <Widget>[
//       Text(''),

//     ],
//   ),
// ),

// showDialog(
//   context: context,
//   builder: (context) {
//     return const AlertDialog(
//       content: Text("OK"),
//     );
//   },
// );
// Navigator.push(
//     context,
//     MaterialPageRoute(
//         builder: (context) => const Launch()));

// Navigator.push(
//     context,
//     MaterialPageRoute(
//         builder: (context) => const admin()));

// Navigator.push(
//     context,
//     MaterialPageRoute(
//         builder: (context) => const Launch()));
