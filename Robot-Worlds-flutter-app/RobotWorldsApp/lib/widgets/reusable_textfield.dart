import 'package:flutter/material.dart';

class CustomTextField extends StatefulWidget {
  const CustomTextField(
      {Key? key, required this.hintText, required this.controller})
      : super(key: key);

  final String hintText;
  final TextEditingController controller;

  @override
  _CustomTextInputState createState() => _CustomTextInputState();
}

class _CustomTextInputState extends State<CustomTextField> {
  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10.0, vertical: 10.0),
        child: TextField(
          key: ValueKey(widget.hintText),
            controller: widget.controller,
            decoration: InputDecoration(
              border: const OutlineInputBorder(),
              hintText: widget.hintText,
            ),
            style: const TextStyle(
              color: Colors.black,
            )));
  }
}
