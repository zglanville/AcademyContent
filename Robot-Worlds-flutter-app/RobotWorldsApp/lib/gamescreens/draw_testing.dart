import 'package:flame/game.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:robot_worlds_app/controllers/client_control.dart';
import '../screens/server_select_screen.dart';
import '../screens/admin_button_screen.dart';
import 'game_home.dart';
import 'package:flame/flame.dart';

class DrawWorld extends StatelessWidget {
  const DrawWorld({Key? key}) : super(key: key);
     @override
    Widget build(BuildContext context) {
      return MaterialApp(
        home: Scaffold(
          appBar: AppBar(
            title: Text("Image from assets"),
          ),
          body: Image.asset('assets/images/grass.png'), //   <--- image
        ),
      );
    }

  // @override
  // Widget build(BuildContext context) {
  //   return Scaffold(
  //     appBar: AppBar(
  //       title: const Text('Custom paint Demo'),
  //     ),
  //     body: Container(
  //       child: Center(
  //         child: CustomPaint(
  //           size: const Size(300, 200),
  //           painter: LinePainter(),
  //         ),
  //       ),
  //     ),
  //   );
  // }
}

class LinePainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    var paint = Paint()
      ..color = Colors.teal
      ..strokeWidth = 15;

    Offset start = Offset(0, size.height);
    Offset end = Offset(size.width, size.height);

    canvas.drawLine(start, end, paint);

    Offset start1 = Offset(0, 0);
    Offset end1 = Offset(size.width, 0);

    canvas.drawLine(start1, end1, paint);

    start = Offset(0, size.height);
    end = Offset(0, 0);

    canvas.drawLine(start, end, paint);

    start = Offset(size.width, 0);
    end = Offset(size.width, size.height);

    canvas.drawLine(start, end, paint);

    canvas.drawCircle(Offset(size.height/2,size.width/2), 10, paint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return false;
  }
}
