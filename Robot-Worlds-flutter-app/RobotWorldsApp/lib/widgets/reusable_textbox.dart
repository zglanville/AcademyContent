import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CustomTextBox extends StatelessWidget {
  final String boxText;
  final double height;

  const CustomTextBox({Key? key, required this.boxText, required this.height})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
        key: ValueKey(boxText),
        height: height,

        child: Text(
          boxText, textAlign: TextAlign.center,
          style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
        ));
  }
}
