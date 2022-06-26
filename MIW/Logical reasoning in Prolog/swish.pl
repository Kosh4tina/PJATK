po_prawej(RightflatId, LeftflatId) :- RightflatId is LeftflatId + 1.
po_lewej(LeftflatId, RightflatId) :- po_prawej(RightflatId, LeftflatId).
obok(flat1, flat2) :- po_prawej(flat1, flat2).
obok(flat1, flat2) :- po_lewej(flat1, flat2).

start(house, Zebra_owner) :-
    house = [
            flat(1, Nationality1, Door1, Pet1, Car1, Drink1),
			flat(2, Nationality2, Doro2, Pet2, Car2, Drink2),
            flat(3, Nationality3, Door3, Pet3, Car3, Drink3),
            flat(4, Nationality4, Door4, Pet4, Car4, Drink4),
            flat(5, Nationality5, Door5, Pet5, Car5, Drink5)
            ],
    member(flat(_, englishman, red, _, _, _), house),
    member(flat(_, spaniard, _, dog, _, _), house),    
    member(flat(_, _, green, _, _, cocoa), house),    
    member(flat(_, ukrainian, _, _, _, eggnog), house),    
    member(flat(flatA, _, green, _, _, _), house),    
    member(flat(flatB, _, ivory, _, _, _), house),   
    po_prawej(flatA, flatB),
    member(flat(_, _, _, snails, oldsmobile, _), house),    
    member(flat(_, _, yellow, _, ford, _), house),    
    member(flat(3, _, _, _, _, milk), house),    
    member(flat(1, norwegian, _, _, _, _), house),    
    member(flat(flatC, _, _, _, chevrolet, _), house),    
    member(flat(flatD, _, _, fox, _, _), house),    
    obok(flatC, flatD),
    member(flat(flatE, _, _, _, ford, _), house),    
    member(flat(flatF, _, _, horse, _, _), house),  
    obok(flatE, flatF),
    member(flat(_, _, _, _, mercedes, orange_juice), house),    
    member(flat(_, japanese, _, _, volkswagen, _), house),    
    member(flat(flatG, norwegian, _, _, _, _), house),
	member(flat(flatH, _, blue, _, _, _), house),    
	obok(flatG, flatH),
    member(flat(_, norwegian, _, _, _, water), house),
    member(flat(_, Zebra_owner, _, zebra, _, _), house).