kawalerka([bathroom, hall]).
showLocations([First|Second]) :- write('in kawalerka: '), write(First), write(' '),nl, showLocations(Second),nl.
show_kawalerka:- kawalerka(Rooms), showLocations(Rooms).


location(door, 'small corridor').
location(kitchen, 'my kitchen').
location(bath, 'my bathroom').
location(window, 'my window').
location(workplace, 'my working place').
location(sleepingzone, 'my sleeping zone').

path(door, kitchen, right).
path(kitchen, door, back).

path(door, bath, left).
path(bath, door, back).

path(door, window, forward).
path(window, door, back).

path(window, workplace, right).
path(workplace, window, back).

path(window, sleepingzone, left).
path(sleepingzone, window, back).

:- dynamic position/2, na/2, pod/2, obok/2, w/2, plecak/1.

setGame :-
    writeln('setting...'),
    retractall(position(_,_)),
    			  assert(position(player, door)),
    retractall(w(_,_)),
    retractall(na(_,_)),
    retractall(obok(_,_)),
    retractall(pod(_,_)),
    retractall(plecak(_)),
                   assert(w(closet, boots)),
                   assert(na(table, laptop)),
                   assert(obok(tv, book)),
    			   assert(obok(tv, cup)),
                   assert(pod(chair, shirt)),
                   assert(w(fridge, pizza)),
                   assert(pod(bed, hantel)),
                   assert(position(closet, door)),
                   assert(position(table, workplace)),
                   assert(position(chair, workplace)),
                   assert(position(tv, workplace)),
                   assert(position(fridge,kitchen)),
                   assert(position(bed, sleepingzone)).

whereiam :-
    write('You are at '),
    position(player, X),
    location(X,Y),
    write(Y), nl,
    write('You can move: '), nl,
    path(X,Z,Poz), location(Z,O),
    write(O),write(' -> '), write(Poz), nl,fail.
	
spojzGlobalnie(X) :- na(X,Y), write(' on witch you have: '),write(Y),nl,spojzGlobalnie(Y).
spojzGlobalnie(X) :- pod(X,Y), write(' under witch you have: '),write(Y),nl,spojzGlobalnie(Y).
spojzGlobalnie(X) :- obok(X,Y), write(' near witch you have: '), write(Y),nl,spojzGlobalnie(Y).
spojzGlobalnie(X) :- w(X,Y), write(' in witch you have: '), write(Y), nl,spojzGlobalnie(Y).

spojz :-
    position(player, X),
    write('Hear I can see: '), position(Y,X),nl,write(Y),
	spojzGlobalnie(Y).


wez(Y) :- na(X,Y), assert(plecak(Y)), write(Y) , write(' was taken from the '), write(X), retract(na(X,Y)).
wez(Y) :- pod(X,Y), assert(plecak(Y)), write(Y) , write(' was taken from under the '), write(X), retract(pod(X,Y)).
wez(Y) :- obok(X,Y), assert(plecak(Y)), write(Y) , write(' was taken near the '), write(X), retract(obok(X,Y)).
wez(Y) :- w(X,Y), assert(plecak(Y)), write(Y) , write(' was taken from the '), write(X), retract(w(X,Y)).

pokazPlecak :- write('You have in your bag: '),nl,plecak(X), write(X),nl,fail.

odlozNa(X,Y) :- retract(plecak(X)), assert(na(Y,X)), write('Was put '), write(X), write(' on the '), write(Y).
odlozPod(X,Y) :- retract(plecak(X)), assert(pod(Y,X)), write('Was put '), write(X), write(' under the '), write(Y).
odlozObok(X,Y) :- retract(plecak(X)), assert(obok(Y,X)), write('Was put '), write(X), write(' near the '), write(Y).
odlozW(X,Y) :- retract(plecak(X)), assert(w(Y,X)), write('Was put '), write(X), write(' inside the '), write(Y).


move(items) :-
    pokazPlecak.

move(Pickup) :-
    wez(Pickup).

move(place_on):-
    writeln('Which item?'),
    read(Item),
    writeln('Where?'),
    read(Place),
    odlozNa(Item, Place).

move(place_under):-
    writeln('Which item?'),
    read(Item),
    writeln('Where?'),
    read(Place),
    odlozPod(Item, Place).
    
move(place_near):-
    writeln('Which item?'),
    read(Item),
    writeln('Where?'),
    read(Place),
    odlozObok(Item, Place).
    
move(place_inside):-
    writeln('Which item?'),
    read(Item),
    writeln('Where?'),
    read(Place),
    odlozW(Item, Place).
    
move(showpaths) :- 
    whereiam.

move(look) :- 
    spojz.

move(Direction) :- 
    position(player, CurrentPos),
    path(CurrentPos, NextPos, Direction),
    write('player go '), write(Direction), write('...'),
    retract(position(player, CurrentPos)),
    assert(position(player, NextPos)),
    nl,
    !.   

move(_) :- % default message, when invalid command was typed
    nl.

moveHandler :- % end of main loop
    position(player, gameOver),
    writeln('The game is over. Thanks for playing!'),
    !.

moveHandler :- % main loop
    nl,
    writeln('What will player do?'),
    read(Move),
    call(move(Move)),
    moveHandler.

start :- 
    setGame,
    writeln('New game started!'),
    moveHandler.

