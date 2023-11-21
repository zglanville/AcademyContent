/*import 'dart:math';
import 'package:provider/provider.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:robot_worlds_app/controllers/world_controller.dart';
import 'package:robot_worlds_app/models/robot_model.dart';
import 'package:robot_worlds_app/widgets/reusable_appbar.dart';

class GridWorld extends StatefulWidget {
  const GridWorld({Key? key}) : super(key: key);

  @override
  _GridWorldState createState() => _GridWorldState();
}

class _GridWorldState extends State<GridWorld> {
  @override
  void initState() {
    Provider.of<WorldControl>(context, listen: false).getWorldDetails();
    super.initState();
  }

  int gridWidth() {
    WorldControl notifier = Provider.of<WorldControl>(context);
    notifier.getBots();
    var open = notifier.openSpaces.length;
    return sqrt(open).toInt();
  }

  @override
  Widget build(BuildContext context) {
    WorldControl notifier = Provider.of<WorldControl>(context);
    var open = notifier.openSpaces;
    var icon = const Icon(Icons.twenty_one_mp_outlined);
    return MaterialApp(
        home: Scaffold(
            appBar: const CustomAppBar(
              appBarText: "World War Robot",
            ),
            body: Center(
                child: Column(children: [
                  const Padding(padding: EdgeInsets.only(top: 15.0)),
              Container(
                  //alignment: center.,
                  height: 350,
                  width: 350,
                  decoration: const BoxDecoration(
                    color: Color(0xffffffff),
                    border: Border(
                        top: BorderSide(width: 5.0, color: Color(0xFF000000)),
                        left: BorderSide(width: 5.0, color: Color(0xFF000000)),
                        right: BorderSide(width: 5.0, color: Color(0xFF000000)),
                        bottom:
                            BorderSide(width: 5.0, color: Color(0xFF000000))),
                  ),
                  child: GridView.count(
                      physics: const NeverScrollableScrollPhysics(),
                      primary: false,
                      padding: const EdgeInsets.all(10),
                      crossAxisSpacing: 5,
                      mainAxisSpacing: 5,
                      crossAxisCount: gridWidth(),
                      children:
                          List.generate(notifier.openSpaces.length, (index) {
                        return Container(
                          key: Key(open[index].toString()),
                          child: icon,
                          color: Colors.red[300],
                        );
                      })))
            ]))));
  }
}

IconData getCellIcon(List<Robot> robots, String coordinates) {
  for (var robot in robots) {
    if (robot.position.toString() == coordinates) {
      print(coordinates);
      print(robot.position.toString());
      return Icons.android;
    } else {
      return Icons.one_k_sharp;
    }
  }
  return Icons.twenty_one_mp_outlined;
}*/
