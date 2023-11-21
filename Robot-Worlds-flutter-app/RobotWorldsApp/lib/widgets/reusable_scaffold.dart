import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:robot_worlds_app/widgets/reusable_appbar.dart';

class CustomScaffold extends StatelessWidget {
  final String appBarText;
  final List<Widget> children;

  const CustomScaffold(
      {Key? key, required this.appBarText, required this.children})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: CustomAppBar(
          appBarText: appBarText,
        ),
        body: Padding(
            padding: const EdgeInsets.all(20.0),
            child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: children,
            ))));
  }
}
