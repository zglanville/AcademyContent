class Worlds {
  String name = '';
  int width = 0;
  int height = 0;
  Map config = {};
  List obstacles = [];
  List mines = [];
  List pits = [];
  List edges = [];
  List openSpaces = [];
  int id = 0;


  Worlds(this.name, this.width, this.height);

  Worlds.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    height = json['height'];
    width = json['width'];
    config = json['config'];
    obstacles = json['obstacles'];
    mines = json['mines'];
    pits = json['pits'];
    edges = json['edges'];
    openSpaces = json['openSpaces'];
    id = json['id'];
  }

  Map<String, dynamic> toJson() =>
      {
        'name': name,
        'height': height,
        'width': width,
        'config': config,
        'obstacles': obstacles,
        'mines': mines,
        'pits': pits,
        'edges': edges,
        'openSpaces': openSpaces,
        'id': id,
      };
}