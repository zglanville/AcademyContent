import 'dart:typed_data';
import 'dart:ui' as ui;
// import 'package:flame/flame.dart';
import 'package:flame/game.dart';
import 'package:flame/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:provider/provider.dart';
// import 'package:robot_worlds_app/player/player_sprite.dart';

import '../models/obstacle_model.dart';
import '../controllers/world_controller.dart';
// import 'package:image/image.dart' as imagePack;

class GameStart extends FlameGame {
  // var initialize;

  @override
  void render(Canvas canvas) {
    Rect background = const Rect.fromLTRB(10, 5, 10, 5);
    Paint paint = Paint()..color.blue;
    canvas.drawRect(background, paint);
    super.render(canvas);
  }

  // void update(double t) {}
}

// Future<void> get initialize async {
//   // YOUR STUFF HERE
// }

class GameScreen extends StatefulWidget {
  const GameScreen({Key? key}) : super(key: key);
  // static final _game = GameStart();

  @override
  _GameScreenState createState() => _GameScreenState();
}

class _GameScreenState extends State {
  ui.Image? _image;

  @override
  void initState() {
    _loadImage();
    super.initState();
  }

  _loadImage() async {
    ByteData bd = await rootBundle.load("assets/images/rock2-resize.png");

    final Uint8List bytes = Uint8List.view(bd.buffer);

    final ui.Codec codec = await ui.instantiateImageCodec(bytes);

    final ui.Image image = (await codec.getNextFrame()).image;

    setState(() => _image = image);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Robot World'),
      ),
      body: Center(
        child: Stack(children: <Widget>[
          Image.asset('assets/images/grass-resize.png'),
          CustomPaint(
            size: const Size(300, 200),
            painter: LinePainter(
                _image,
                Provider.of<WorldControl>(context, listen: false)
                    .getWorldObstacles()),
          ),
          Positioned.fill(
            child: Align(
              alignment: Alignment.bottomLeft,
              child: Column(
                children: <Widget>[
                  IconButton(
                      onPressed:() {
                        Provider.of<WorldControl>(context, listen: false)
                          .commandRobot('testName', 'forward', ['1'])
                          .then((String value) => showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    content: Text(value.toString()),
                                  );
                                },
                              ));
                      }, 
                      icon: const Icon(Icons.arrow_circle_up_rounded)),
                      IconButton(
                           onPressed:() {
                        Provider.of<WorldControl>(context, listen: false)
                          .commandRobot('testName', 'back', ['1'])
                          .then((String value) => showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    content: Text(value.toString()),
                                  );
                                },
                              ));
                      }, 
                      icon: const Icon(Icons.arrow_circle_down_rounded)),
                       IconButton(
                            onPressed:() {
                        Provider.of<WorldControl>(context, listen: false)
                          .commandRobot('testName', 'turn', ['left'])
                          .then((String value) => showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    content: Text(value.toString()),
                                  );
                                },
                              ));
                      }, 
                      icon: const Icon(Icons.arrow_left_rounded)),
                      IconButton(
                           onPressed:() {
                        Provider.of<WorldControl>(context, listen: false)
                          .commandRobot('testName', 'turn', ['right'])
                          .then((String value) => showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    content: Text(value.toString()),
                                  );
                                },
                              ));
                      }, 
                      icon: const Icon(Icons.arrow_right_rounded)),
                      //   IconButton(
                      // onPressed: upArrow, icon: const Icon(Icons.arrow_downward_rounded)),
                      //   IconButton(
                      // onPressed: upArrow, icon: const Icon(Icons.arrow_right_rounded)),
                      //   IconButton(
                      // onPressed: upArrow, icon: const Icon(Icons.arrow_left_rounded)),
                ],
              ),
            ),
          ),
          // IconButton(onPressed: upArrow, icon: const Icon(Icons.arrow_upward)),

          // IconButton(onPressed: upArrow, icon: const Icon(Icons.arrow_downward)),
          // ButtonTheme(
          //   minWidth: 200.0,
          //   height: 100.0,
          //   child: FloatingActionButton(
          //     onPressed: () {},
          //     child: Text("test"),

          //     //  : FloatingActionButtonLocation.centerFloat,
          //   ),
          // ),
          // GridView.count(
          //   crossAxisCount: 4,
          //   primary: false,
          //   padding: const EdgeInsets.all(5),
          //   crossAxisSpacing: 2,
          //   mainAxisSpacing: 2,
          //   children:  <Widget>[
          //     FloatingActionButton(
          //       onPressed: upArrow,
          //       child: Icon(Icons.arrow_circle_up),
          //     ),

          //     Column(
          //       // crossAxisAlignment: CrossAxisAlignment.start,
          //       // mainAxisSize: MainAxisSize.min,
          //       children: const <Widget>[
          //           FloatingActionButton(
          //       onPressed: upArrow,
          //       child: Icon(Icons.arrow_circle_up),
          //     ),
          //      FloatingActionButton(
          //       onPressed: upArrow,
          //       child: Icon(Icons.arrow_circle_up),
          //     ),
          //       ],

          //     ),
          //      FloatingActionButton(
          //       onPressed: upArrow,
          //       child: Icon(Icons.arrow_circle_up),
          //     ),

          //   ],
          // ),
          //  const FloatingActionButton(
          //    onPressed: upArrow,
          //    child: Icon(Icons.arrow_circle_up),

          //    ),

          // AlertDialog(
          //   content: Text('OK'),
          // ),
        ]),
      ),
    );
  }
}

void upArrow() {}

class LinePainter extends CustomPainter {
  ui.Image? image;
  List<Obstacle> obsList;

  LinePainter(this.image, this.obsList) : super();
  @override
  Future paint(Canvas canvas, Size size) async {
    var paint = Paint()
      ..color = Colors.teal
      ..strokeWidth = 15;
    // Rect background = Rect.fromLTWH(0, 0, size.width, size.height);
    // canvas.drawRect(background, paint);

    var obsPaint = Paint()
      ..color = Colors.red
      ..strokeWidth = 10;

    // Image img = Image.asset('assets/images/rock2.png');
    Offset offset = Offset(15, 15);
    // var image = await load('assets/images/rock2.png');
    if (image != null) {
      // ui.Image img = image!;
      for (Obstacle element in obsList) {
        canvas.drawImage(image!,
            Offset(element.x.toDouble(), element.y.toDouble()), Paint());
      }
      // canvas.drawImage(image!, Offset(0, 0), Paint());
      // canvas.drawImage(image!, Offset(0, size.width), Paint());
      // canvas.drawImage(image!, Offset(size.height, 0), Paint());
      // canvas.drawImage(image!, Offset(size.height, size.width), Paint());
    }
    // canvas.drawCircle(Offset(size.height / 2, size.width / 2), 10, obsPaint);

    // Offset start = Offset(0, 0);
    // Offset end = Offset(0, 600);

    // canvas.drawLine(start, end, paint);

    // start = Offset(0, 0);
    // end = Offset(600, 0);

    // canvas.drawLine(start, end, paint);

    // Offset start1 = Offset(0, 0);
    // Offset end1 = Offset(size.width, 0);

    // canvas.drawLine(start1, end1, paint);

    // start = Offset(0, size.height);
    // end = Offset(0, 0);

    // canvas.drawLine(start, end, paint);

    // start = Offset(size.width, 0);
    // end = Offset(size.width, size.height);

    // canvas.drawLine(start, end, paint);

    // canvas.drawCircle(Offset(size.height/2,size.width/2), 10, paint);
  }

  // Future<ui.Image> load(String asset) async {
  // ByteData data = await rootBundle.load(asset);
  // ui.Codec codec = await ui.instantiateImageCodec(data.buffer.asUint8List());
  // ui.FrameInfo fi = await codec.getNextFrame();
  // return fi.image;
  //}
// Future<ui.Image> getUiImage(String imageAssetPath, int height, int width) async {
//   final ByteData assetImageByteData = await rootBundle.load(imageAssetPath);
//   // image.Image baseSizeImage = image.decodeImage(assetImageByteData.buffer.asUint8List());
//   // image.Image resizeImage = image.copyResize(baseSizeImage, height: height, width: width);
//   ui.Codec codec = await ui.instantiateImageCodec(image.encodePng(resizeImage));
//   ui.FrameInfo frameInfo = await codec.getNextFrame();
//   return frameInfo.image;
// }
  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return false;
  }
}
