import 'package:provider/provider.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:robot_worlds_app/screens/role_select_screen.dart';
import 'package:robot_worlds_app/widgets/reusable_elevated_button.dart';
import 'package:robot_worlds_app/widgets/reusable_listtile.dart';
import 'package:robot_worlds_app/widgets/reusable_scaffold.dart';
import 'package:robot_worlds_app/widgets/reusable_textbox.dart';
import 'package:robot_worlds_app/controllers/world_controller.dart';

class WorldsList extends StatefulWidget {
  const WorldsList({Key? key}) : super(key: key);

  @override
  _WorldsListState createState() => _WorldsListState();
}

class _WorldsListState extends State<WorldsList> {
  @override
  void initState() {
    Provider.of<WorldControl>(context, listen: false).getWorlds();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<WorldControl>(context);
    return CustomScaffold(appBarText: 'Saved Worlds List', children: <Widget>[
      const CustomTextBox(boxText: 'Please select a world to load', height: 20),
      const Padding(padding: EdgeInsets.only(top: 15.0)), //empty SizedBox for padding
      Container(
          height: 350,
          decoration: const BoxDecoration(
            color: Color(0xffffffff),
            border: Border(
                top: BorderSide(width: 5.0, color: Color(0xFF000000)),
                left: BorderSide(width: 5.0, color: Color(0xFF000000)),
                right: BorderSide(width: 5.0, color: Color(0xFF000000)),
                bottom: BorderSide(width: 5.0, color: Color(0xFF000000))),
          ),
          child: ListView.builder(
              scrollDirection: Axis.vertical,
              shrinkWrap: true,
              itemCount: provider.world.worlds.length,
              itemBuilder: (context, index) => Card(
                  elevation: 6,
                  margin: const EdgeInsets.all(10),
                  child: CustomListTile(
                      //icon: Icons.favorite,
                      title: provider.world.worlds[index].toString(),
                      subtitle: " ",
                      buttonText: 'Load',
                      onPressed: () {
                        var name = provider.world.worlds[index].toString();
                        showDialog(
                            context: context,
                            builder: (context) {
                              return AlertDialog(
                                  content: Text(
                                      "Are you sure you want to load $name?"),
                                  actions: [
                                    TextButton(
                                        child: const Text("Yes",
                                            style:
                                                TextStyle(color: Colors.white)),
                                        style: ElevatedButton.styleFrom(
                                            primary: Colors.blue),
                                        onPressed: () {
                                          Provider.of<WorldControl>(context,
                                                  listen: false)
                                              .loadSavedWorld(provider
                                                  .world.worlds[index]
                                                  .toString());
                                          Navigator.of(context).pop();
                                          Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const Selection()));
                                        }),
                                    TextButton(
                                        child: const Text("No",
                                            style:
                                                TextStyle(color: Colors.white)),
                                        style: ElevatedButton.styleFrom(
                                            primary: Colors.blue),
                                        onPressed: () {
                                          Navigator.of(context).pop();
                                        })
                                  ]);
                            });
                      })))),
      const Padding(padding: EdgeInsets.all(10.0)),
      CustomElevatedButton(
        buttonText: "Skip",
        onPressed: () {
          Navigator.push(context,
              MaterialPageRoute(builder: (context) => const Selection()));
        },
        primary: Colors.blue,
      ),
      // const Padding(padding: EdgeInsets.all(20.0)),
    ]);
  }
}
/*            child: ListView.builder(
                    itemBuilder: (context, index) {
                      return GestureDetector(
                        child: Card(
                          child: Padding(
                            padding: const EdgeInsets.all(16.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: <Widget>[
                                Text(
                                  (_worlds[index].id).toString() +
                                      '. ' +
                                      _worlds[index].name,
                                  style: const TextStyle(
                                      fontSize: 24, fontWeight: FontWeight.bold),
                                ),
                                Text(
                                  (_worlds[index].width).toString() +
                                      'x' +
                                      (_worlds[index].height).toString(),
                                  style: TextStyle(
                                      fontSize: 11, color: Colors.grey.shade600),
                                ),
                              ],
                            ),
                          ),
                        ),
                        onTap: () {
                          Navigator.push(context,
                              MaterialPageRoute(builder: (context) => Selection()));
                        },
                      );
                    },
                    itemCount: _worlds.length,
                    )*/
