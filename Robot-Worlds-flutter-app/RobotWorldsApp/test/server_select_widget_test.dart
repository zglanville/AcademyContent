import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:robot_worlds_app/screens/server_select_screen.dart';

void main() {
  testWidgets('when', (WidgetTester tester) async {
    await tester.pumpWidget(const MaterialApp(home: ServerSelect()));

    // Check for Text Fields and Button.
    expect(find.byKey(const Key('Server IP address')), findsOneWidget);
    expect(find.byKey(const Key('Server port')), findsOneWidget);
    expect(find.byKey(const Key('Connect')), findsOneWidget);

    // Add text to the fields. Keys of CustomTextField widgets are their hintText.
    //await tester.enterText(
    //    find.byKey(const Key('Server IP address')), 'localhost');
    //await tester.enterText(find.byKey(const Key('Server port')), "5000");

    // Tap the add button. Keys of CustomElevatedButton widgets are their buttonText.
    //await tester.tap(find.byKey(const Key("Connect")));

    // Rebuild the widget with the new item.
    //await tester.pump();

    // Expect to find the item on screen.
    //expect(find.byType(WorldsList), findsOneWidget);

    // Ensure that the item is no longer on screen.
    //expect(find.byType(ServerSelect), findsNothing);
  });
}
