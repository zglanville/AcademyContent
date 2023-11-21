import 'package:http/http.dart' as http;
import 'dart:convert';

import '../data.dart';

class ClientControl {
  String ip = Data.ip;
  String port = Data.port;

  Future getRequest(String url) async {
    var requestHeader = {
      "Content-type": "application/json",
      "Accept": "application/json",
      "Access-Control_Allow_Origin": "*",
    };
    http.Response response;
    response = await http.get(Uri.parse('http://' + ip + ':' + port + url),
        headers: requestHeader);

    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(response.body);
      // print(jsonResponse);
      return jsonResponse;
    } else {
      // throw Exception('Failed to load');
      return "Failed to load.";
    }
  }

  Future<Object> postRequest(String url, Object body) async {
    http.Response postResponse;
    print('BODY: ' + body.toString());
    postResponse = await http.post(
      Uri.parse('http://' + ip + ':' + port + url),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(body),
    );

    print(postResponse.statusCode);
    if (postResponse.statusCode == 200 | 201) {
      var jsonResponse = jsonDecode(postResponse.body);
      // print(jsonResponse);
      return jsonResponse;
    } else {
      throw Exception('Failed to load');
    }
  }

  Future getPing(String url) async {
    var requestHeader = {
      "Content-type": "application/json",
      "Accept": "application/json",
      "Access-Control_Allow_Origin": "*",
    };
    http.Response response;
    response = await http.get(Uri.parse('http://' + ip + ':' + port + url),
        headers: requestHeader);

    if (response.statusCode == 200) {
      return response.statusCode;
    } else {
      // throw Exception('Failed to load');
      return "Failed to load.";
    }
  }

  // Future<Object> deleteRequest(String url, Object body) async {
  //   http.Response postResponse;
  //   postResponse = await http.delete(
  //     Uri.parse('http://' + ip + ':' + port + url),
  //     headers: <String, String>{
  //       'Content-Type': 'application/json; charset=UTF-8',
  //     },
  //     body: jsonEncode(body),
  //   );

  //   if (postResponse.statusCode == 200) {
  //     var jsonResponse = jsonDecode(postResponse.body);
  //     // print(jsonResponse);
  //     return jsonResponse;
  //   } else {
  //     throw Exception('Failed to load');
  //   }
  // }

  Future<Object> deleteRequest(String url) async {
    http.Response postResponse;
    postResponse = await http.delete(
      Uri.parse('http://' + ip + ':' + port + url),
    );

    if (postResponse.statusCode == 200) {
      // var jsonResponse = jsonDecode(postResponse.body);
      // print(jsonResponse);
      return "OK";
    } else {
      throw Exception('Failed to load');
    }
  }

  Future<Object> deleteObsRequest(String url, Object body) async {
    http.Response postResponse;
    postResponse = await http.delete(
        Uri.parse('http://' + ip + ':' + port + url),
        body: jsonEncode(body));

    if (postResponse.statusCode == 200) {
      // var jsonResponse = jsonDecode(postResponse.body);
      // print(jsonResponse);
      return "OK";
    } else {
      throw Exception('Failed to load');
    }
  }

  Future<Object> postWorldRequest(String url) async {
    http.Response postResponse;
    postResponse = await http.post(
      Uri.parse('http://' + ip + ':' + port + url),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    if (postResponse.statusCode == 201) {
      // var jsonResponse = jsonDecode(postResponse.body);
      // print(jsonResponse);
      return 'Success';
    } else {
      return 'Failed to save';
      // throw Exception('Failed to load');
    }
  }

  // Future<Object> fetchCurrentWorld() async {
  //   var jsonResponse = await getRequest('/world');
  //   print(jsonResponse);
  //   return jsonResponse;
  // }

  // Future<Object> loadWorld(String worldName) async {
  //   var jsonResponse = await getRequest('/admin/load/' + worldName);
  //   print(jsonResponse);
  //   return jsonResponse;
  // }

  // Future<Object> getAllRobots() async {
  //   var jsonResponse = await getRequest('/admin/robots');
  //   print(jsonResponse);
  //   return jsonResponse;
  // }

  // Future<Object> postRobot(Object body, String robotName) async {
  //   var jsonResponse = await postRequest('/robot/' + robotName, body);
  //   print(jsonResponse);
  //   return jsonResponse;
  // }

  // Future<Object> createObstacles(Object body) async {
  //   var jsonResponse = await postRequest('/admin/obstacles', body);
  //   print(jsonResponse);
  //   return jsonResponse;
  // }
}
