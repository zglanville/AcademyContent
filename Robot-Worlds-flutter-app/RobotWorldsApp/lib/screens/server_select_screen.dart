import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:robot_worlds_app/controllers/world_controller.dart';
import 'package:robot_worlds_app/widgets/reusable_elevated_button.dart';
import 'package:robot_worlds_app/widgets/reusable_scaffold.dart';
import 'package:robot_worlds_app/widgets/reusable_textbox.dart';
import 'package:robot_worlds_app/widgets/reusable_textfield.dart';
import 'package:robot_worlds_app/screens/worlds_list_screen.dart';
import '../data.dart';

class ServerSelect extends StatefulWidget {
  const ServerSelect({Key? key}) : super(key: key);

  @override
  _ServerSelectState createState() => _ServerSelectState();
}

class _ServerSelectState extends State<ServerSelect> {
  TextEditingController serverIPController = TextEditingController();
  TextEditingController serverPortController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return CustomScaffold(appBarText: 'Select Server', children: [
      const Padding(padding: EdgeInsets.only(bottom: 5.0),
          child: CustomTextBox(height: 20, boxText: 'Enter server IP and port')
      ),
      const Padding(padding: EdgeInsets.all(0.0)),
      CustomTextField(
          controller: serverIPController, hintText: 'Server IP address'),
      const Padding(padding: EdgeInsets.all(0.0)),
      CustomTextField(
          controller: serverPortController, hintText: 'Server port'),
      const Padding(padding: EdgeInsets.only(bottom: 40.0)),
      CustomElevatedButton(
          buttonText: "Connect",
          primary: Colors.blue,
          onPressed: () async {
            var ip = serverIPController.text;
            var port = serverPortController.text;
            Data.ip = serverIPController.text;
            Data.port = serverPortController.text;
            final provider = Provider.of<WorldControl>(context, listen: false);
            int response = await provider.serverRequest(ip, port);
            provider.serverRequest(ip, port);
            //print(response);
            if (response == 200) {
              ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                content: Text('SUCCESS'),
              ));
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const WorldsList()));
            } else {
              ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Failed to connect to server')));
              throw Exception('Failed to connect to server');
            }
          }),
      //const Padding(padding: EdgeInsets.only(top: 15.0)),
      /*CustomElevatedButton(
          buttonText: "localhost/5000",
          primary: Colors.cyan,
          onPressed: () async {
            var ip = "localhost";
            var port = "5000";
            Data.ip = ip;
            Data.port = port;
            int response = await serverRequest(ip, port);
            serverRequest(ip, port);
            //print(response);
            if (response == 200) {
              ScaffoldMessenger.of(context)
                  .showSnackBar(const SnackBar(content: Text('SUCCESS')));
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const WorldsList()));
            } else {
              ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Failed to connect to server')));
              throw Exception('Failed to connect to server');
            }
          }),*/
      const Padding(padding: EdgeInsets.all(80.0)),
    ]);
  }
}
