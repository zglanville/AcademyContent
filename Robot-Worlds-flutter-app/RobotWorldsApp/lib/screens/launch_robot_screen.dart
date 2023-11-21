import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:robot_worlds_app/controllers/world_controller.dart';
import 'package:robot_worlds_app/gamescreens/world_screen.dart';
import 'package:robot_worlds_app/widgets/reusable_elevated_button.dart';
import 'package:robot_worlds_app/widgets/reusable_scaffold.dart';
import 'package:robot_worlds_app/widgets/reusable_textbox.dart';
import 'package:robot_worlds_app/widgets/reusable_textfield.dart';

class Launch extends StatefulWidget {
  const Launch({Key? key}) : super(key: key);

  @override
  _LaunchState createState() => _LaunchState();
}

class _LaunchState extends State<Launch> {
  final List<String> _robots = ['Sniper', 'Gunner', 'Tank', 'Miner'];
  var _selectedRobot;
  TextEditingController robotNameController = TextEditingController();
  TextEditingController robotTypeController = TextEditingController();

  @override
  void initState() {
    //Provider.of<WorldControl>(context, listen: false).getWorldDetails();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    // WorldControl notifier = Provider.of<WorldControl>(context);
    return CustomScaffold(appBarText: 'Launch Robot', children: <Widget>[
      const Padding(padding: EdgeInsets.all(20.0)),
      const CustomTextBox(
        boxText: 'Select robot parameters',
        height: 60,
      ),
      CustomTextField(
        controller: robotNameController,
        hintText: 'Enter the name of your robot',
      ),
      const SizedBox(height: 30),
      DropdownButton(
        // controller: robotTypeController, items: ['Sniper', 'Gunner', 'Tank', 'Miner'],;
        hint: const Text('Choose a robot type'),
        // Not necessary for Option 1
        value: _selectedRobot,
        onChanged: (newValue) {
          setState(() {
            _selectedRobot = newValue;
          });
        },
        items: _robots.map((robot) {
          return DropdownMenuItem(
            child: Text(robot),
            value: robot,
          );
        }).toList(),
      ),
      const Padding(padding: EdgeInsets.all(20.0)),
      CustomElevatedButton(
          buttonText: 'Launch',
          primary: Colors.blue,
          onPressed: () async {
            Provider.of<WorldControl>(context, listen: false)
                .commandRobot(robotNameController.text, 'launch', ['1']);

            // var robotName = robotNameController.text;
            // if (response == 200) {
            Navigator.push(context,
                MaterialPageRoute(builder: (context) => const GameScreen()));
            //   }
            //   throw Exception('Failed to connect to server');
          }),
      const Padding(padding: EdgeInsets.all(50.0)),
    ]);
  }
}
