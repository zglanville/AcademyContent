import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class admin extends StatefulWidget {
  const admin({Key? key}) : super(key: key);

  @override
  _adminState createState() => _adminState();
}

class _adminState extends State<admin> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: const Text('ADMIN'),
        )
    );
  }
}
