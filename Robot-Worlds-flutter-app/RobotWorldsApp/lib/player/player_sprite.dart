// import 'package:flame/components.dart';
// import 'package:flame/game.dart';
// // import '../helpers/direction.dart';

// class Player extends SpriteComponent with HasGameRef {
//   // Direction direction = Direction.none;
//   Player()
//       : super(
//           size: Vector2.all(50.0),
//         );

//   @override
//   Future<void> onLoad() async {
//     final sprite = await Sprite.load('robot.png');
//     final size = Vector2.all(128.0);
//     final player = SpriteComponent(size: size, sprite: sprite);

//     // screen coordinates
//     player.position = gameRef.size /
//         2; // Vector2(0.0, 0.0) by default, can also be set in the constructor
//     player.angle = 0; // 0 by default, can also be set in the constructor
//     add(player);
//     // super.onLoad();
//     // sprite = await gameRef.loadSprite('robot.png');
//     // position = gameRef.size / 2;
//   }
// }

// class RayWorldGame extends FlameGame {
//   final Player _player = Player();
//   @override
//   Future<void> onLoad() async {
//     add(_player);
//   }

//   // void onJoypadDirectionChanged(Direction direction) {
//   //   _player.direction = direction;
//   // }
// }
