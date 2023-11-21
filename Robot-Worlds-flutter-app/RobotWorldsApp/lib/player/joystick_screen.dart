// import 'package:flame/components.dart';
// import 'package:flame/game.dart';
// import 'package:flame/sprite.dart';
// import 'package:flutter/widgets.dart';

// class MyGame extends FlameGame with HasDraggableComponents {
//   late JoystickComponent joystick;
//   late JoystickPlayer player;

//   MyGame() {
//     // joystick.
//     // joystick.addObserver(player);
//     add(player);
//     add(joystick);
//   }

//   @override
//   Future<void> onLoad() async {
//     super.onLoad();
//     final image = await images.load('joystick.png');
//     final sheet = SpriteSheet.fromColumnsAndRows(
//       image: image,
//       columns: 6,
//       rows: 1,
//     );
//     final joystick = JoystickComponent(
//       knob: SpriteComponent(
//         sprite: sheet.getSpriteById(1),
//         size: Vector2.all(100),
//       ),
//       background: SpriteComponent(
//         sprite: sheet.getSpriteById(0),
//         size: Vector2.all(150),
//       ),
//       margin: const EdgeInsets.only(left: 40, bottom: 40),
//     );

//     final player = JoystickPlayer(joystick);
//     add(player);
//     add(joystick);
//   }
// }

// class JoystickPlayer extends SpriteComponent with HasGameRef {
//   /// Pixels/s
//   double maxSpeed = 300.0;

//   final JoystickComponent joystick;

//   JoystickPlayer(this.joystick)
//       : super(
//           size: Vector2.all(100.0),
//         ) {
//     anchor = Anchor.center;
//   }

//   @override
//   Future<void> onLoad() async {
//     super.onLoad();
//     sprite = await gameRef.loadSprite('layers/player.png');
//     position = gameRef.size / 2;
//   }

//   @override
//   void update(double dt) {
//     super.update(dt);
//     if (joystick.direction != JoystickDirection.idle) {
//       position.add(joystick.size * maxSpeed * dt);
//       angle = joystick.delta.screenAngle();
//     }
//   }
// }
