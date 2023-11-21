import 'package:flutter/material.dart';

class CustomListTile extends StatelessWidget {
  final String title;
  final String subtitle;
  final String buttonText;
  //final IconData icon;
  final GestureTapCallback onPressed;

  const CustomListTile(
      {Key? key,
      required this.buttonText,
      required this.onPressed,
      required this.title,
      required this.subtitle/*,
      required this.icon*/})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      //leading: Icon(icon),
      title: Text(title),
      subtitle: Text(subtitle),
      trailing: TextButton(
          child: Text(
            buttonText,
            style: const TextStyle(color: Colors.white),
          ),
          style: ElevatedButton.styleFrom(
              minimumSize: const Size(100, 40),
              maximumSize: const Size(100, 40),
              primary: Colors.blue,
              textStyle: const TextStyle(fontWeight: FontWeight.bold)),
          onPressed: onPressed),
    );
  }
}
