import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:robot_worlds_app/screens/admin_button_screen.dart';

void main() {
  testWidgets('when', (WidgetTester tester) async {
    await tester.pumpWidget(const MaterialApp(home: AdminPage()));

    // Check for Text Fields and Button.
    expect(find.byKey(const Key('Admin Commands')), findsOneWidget);
    expect(find.byKey(const Key('Robot name: ')), findsOneWidget);
    expect(find.byKey(const Key('Obstacle x: ')), findsOneWidget);
    expect(find.byKey(const Key('Obstacle y: ')), findsOneWidget);
    expect(find.byKey(const Key('World name: ')), findsOneWidget);
    expect(find.byKey(const Key('Get All Bots')), findsOneWidget);
    expect(find.byKey(const Key('Delete A Bot')), findsOneWidget);
    expect(find.byKey(const Key('Create Obs')), findsOneWidget);
    expect(find.byKey(const Key('Delete Obs')), findsOneWidget);
    expect(find.byKey(const Key('Save World')), findsOneWidget);
    expect(find.byKey(const Key('Load World')), findsOneWidget);

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
