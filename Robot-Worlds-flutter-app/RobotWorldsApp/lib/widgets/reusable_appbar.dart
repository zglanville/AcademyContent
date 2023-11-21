import 'package:flutter/material.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String appBarText;

  const CustomAppBar({
    Key? key,
    required this.appBarText,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppBar(
        backgroundColor: Colors.blue,
        centerTitle: true,
        key: ValueKey(appBarText),
        title: Text(appBarText,
              style: const TextStyle(
                  color: Colors.white,
                  fontSize: 25,
                  fontWeight: FontWeight.bold))
        );
  }

  @override
  Size get preferredSize => const Size.fromHeight(56.0);
}
