CREATE 
(john { name: "John" }),
(anne { name: "Anne" }),
(jessica { name: "Jessica" }),
(matthew { name: "Matthew" }),
(rupert { name: "Rupert" }),
(billy { name:  "Billy" }),
(wiseComment { content: "Wise Comment" }),
(jessicaComment { content: "Jessica Comment" }),
matthew-[:KNOWS]->jessica,
matthew-[:KNOWS]->rupert,
rupert-[:KNOWS]->billy,
john-[:KNOWS]->jessica,
john-[:KNOWS]->anne,
john-[:WROTE]->wiseComment,
matthew-[:LIKES]->wiseComment,
anne-[:LIKES]->wiseComment,
jessica-[:WROTE]->jessicaComment,
rupert-[:LIKES]->jessicaComment,
billy-[:LIKES]->jessicaComment
