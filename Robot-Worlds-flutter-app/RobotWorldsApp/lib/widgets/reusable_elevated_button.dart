import 'package:flutter/material.dart';

class CustomElevatedButton extends StatelessWidget {
  final String buttonText;
  final MaterialColor primary;
  final GestureTapCallback onPressed;

  const CustomElevatedButton(
      {Key? key,
      required this.buttonText,
      required this.onPressed,
      required this.primary})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Expanded(
        child: ElevatedButton(
            onPressed: () {
              onPressed();
            },
            key: ValueKey(buttonText),
            child: Text(buttonText),
            style: ElevatedButton.styleFrom(
              primary: primary,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(30.0),
              ),
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 0),
              minimumSize: const Size(400, 60),
              maximumSize: const Size(400, 60),
              textStyle:
                  const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            )));
  }
}
