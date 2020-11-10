% prg_03_Prolog
% Professor: Thyago Mota
% Student & Author: Austin Gailey
% This program demonstrates an expert system in prolog

begin :-
  init,
  (read(yes) ->
  reset_answers,
  find_show(Show),
  describe(Show), nl,
  confirm
  ;
  write('Bye')).
begin :-
  write('I couldn\'t decide on a show for you to watch.'), nl,
  write('Please try again by typing begin.'), nl.

init :-
  write('Welcome to this ES about tv shows!'), nl,
  write('Which show should you watch next?'), nl,
  write('I am going to ask about your interests.'), nl,
  write('Please answer yes. or no.'), nl,
  write('Ready?'), nl.

reset_answers :-
  retractall(progress(_, _)).
reset_answers.

find_show(Show) :-
  show(Show), !.

confirm :-
  write('Did I make a good recommendation?'), nl,
  (read(yes) ->
  write('Glad I could help!')
  ;
  write('Well, it\'s my programmers fault.'), nl,  
  write('Feel free to start again by typing begin.'), nl).

:- dynamic(progress/2).

% Rules about shows
show(thesimpsons) :-
  animated(yes),
  comedy(yes),
  drama(yes).

show(friends) :-
  comedy(yes),
  classic(yes).

show(scrubs) :-
  medical(yes),
  comedy(yes).

show(howimetyourmother) :-
  comedy(yes),
  drama(yes).

show(rickandmorty) :-
  animated(yes),
  action(yes),
  adult(yes).

show(gameofthrones) :-
  fantasy(yes),
  adult(yes).

show(lost) :-
  fantasy(yes),
  action(yes).

show(startrek) :-
  fantasy(yes).

show(house) :-
  medical(yes),
  drama(yes).

show(thetwilightzone) :-
  horror(yes),
  drama(yes),
  action(yes).

show(strangerthings) :-
  horror(yes),
  drama(yes).

show(24) :-
  action(yes),
  drama(no).

show(peakyblinders) :-
  drama(yes),
  action(yes),
  adult(yes).

show(sherlock) :-
  drama(yes),
  action(yes).

show(bettercallsaul) :-
  drama(yes).

% Show descriptions about the knowledge base
describe(friends) :-
  write('Friends'), nl,
  write('One of the most popular sit-coms of the 90\'s'), nl.

describe(scrubs) :-
  write('Scrubs'), nl,
  write('A funny show about hospital nurses.'), nl.

describe(howimetyourmother) :-
  write('How I Met Your Mother'), nl,
  write('An engaging 9 season sit-com'), nl.

describe(thesimpsons) :-
  write('The Simpsons'), nl,
  write('An animated show about a crazy family'), nl.

describe(rickandmorty) :-
  write('Rick and Morty'), nl,
  write('A crazy scientist and his nephew embark on wild adventures!'), nl.

describe(gameofthrones) :-
  write('Game of Thrones'), nl,
  write('Gory and Political'), nl.

describe(house) :-
  write('House'), nl,
  write('A medical genius with a dark side'), nl.

describe(strangerthings) :-
  write('Stranger Things'), nl,
  write('Show about the disappearance of a boy and weird happenings in Hawkins, Indiana'), nl.

describe(24) :-
  write('24'), nl,
  write('An intense action packed adventure - the whole season takes place in 24 hours'), nl.

describe(bettercallsaul) :-
  write('Better Call Saul'), nl,
  write('A lawyer who is trying to amount to something in his life.').

describe(sherlock) :-
  write('Sherlock'), nl,
  write('About Sherlock Holmes and his detective stories!'), nl.

describe(peakyblinders) :-
  write('Peaky Blinders'), nl,
  write('Set in Britain, this show features a crime ring run by the Shelby family'), nl.

describe(startrek) :-
  write('Star Trek'), nl,
  write('Explore the universe with Scotty and his spaceship!'), nl.

describe(lost) :-
  write('Lost'), nl,
  write('Follow the mysteries of lost island explorers!'), nl.

describe(thetwilightzone) :-
  write('The Twilight Zone'), nl,
  write('Follow characters as they encounter disturbing events!'), nl.

% Questions about the knowledge base
question(animated) :-
  write('Do you like animated shows?'), nl.

question(comedy) :-
  write('Do you enjoy comedy?'), nl.

question(classic) :-
  write('Do you prefer classics?'), nl.

question(fantasy) :-
  write('Do you like fantasy?'), nl.

question(adult) :-
  write('Are you 18+ years of age?'), nl.

question(medical) :-
  write('Do you like shows about hospitals?'), nl.

question(horror) :-
  write('Do you like horror?'), nl.

question(drama) :-
  write('Do you like drama?'), nl.

question(action) :-
  write('Do you like action?'), nl.

option(yes).
option(no).

% Assigns an answer to questions from the knowledge base
comedy(Answer) :-
  progress(comedy, Answer).
comedy(Answer) :-
  \+ progress(comedy, _),
  ask(comedy, Answer, [yes, no]).

classic(Answer) :-
  progress(classic, Answer).
classic(Answer) :-
  \+ progress(classic, _),
  ask(classic, Answer, [yes, no]).

adult(Answer) :-
  progress(adult, Answer).
adult(Answer) :-
  \+ progress(adult, _),
  ask(adult, Answer, [yes, no]).

fantasy(Answer) :-
  progress(fantasy, Answer).
fantasy(Answer) :-
  \+ progress(fantasy, _),
  ask(fantasy, Answer, [yes, no]).

medical(Answer) :-
  progress(medical, Answer).
medical(Answer) :-
  \+ progress(medical, _),
  ask(medical, Answer, [yes, no]).

horror(Answer) :-
  progress(horror, Answer).
horror(Answer) :-
  \+ progress(horror, _),
  ask(horror, Answer, [yes, no]).

drama(Answer) :-
  progress(drama, Answer).
drama(Answer) :-
  \+ progress(drama, _),
  ask(drama, Answer, [yes, no]).

action(Answer) :-
  progress(action, Answer).
action(Answer) :-
  \+ progress(action, _),
  ask(action, Answer, [yes, no]).

animated(Answer) :-
  progress(animated, Answer).
animated(Answer) :-
  \+ progress(animated, _),
  ask(animated, Answer, [yes, no]).

% Asks the Question to the user then saves the Answer
ask(Question, Answer, Options) :-
  question(Question),
  read(Response),
  asserta(progress(Question, Response)),
  Response = Answer.