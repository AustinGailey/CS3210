% Facts about names
peak('Mt. Everest').
peak('The Aconcagua').
peak('Mt. McKinley').
peak('The Kilimanjaro').
peak('Mt. Elbrus').
peak('Mt. Vinson').
peak('The Puncak Jaya').

% Facts about locations
location('Mt. Everest', 'Asia')             :- peak('Mt. Everest').
location('The Aconcagua','South America')   :- peak('The Aconcagua').
location('Mt. McKinley', 'North America')   :- peak('Mt. McKinley').
location('The Kilimanjaro', 'Africa')       :- peak('The Kilimanjaro').
location('Mt. Elbrus', 'Europe')            :- peak('Mt. Elbrus').
location('Mt. Vinson', 'Antartica')         :- peak('Mt. Vinson').
location('The Puncak Jaya', 'Australia')    :- peak('The Puncak Jaya').

% Facts about elevations
elevation('Mt. Everest', 29029)             :- peak('Mt. Everest').
elevation('The Aconcagua', 22841)           :- peak('The Aconcagua').
elevation('Mt. McKinley', 22841)            :- peak('Mt. McKinley').
elevation('The Kilimanjaro', 19340)         :- peak('The Kilimanjaro').
elevation('Mt. Elbrus', 18510)              :- peak('Mt. Elbrus').
elevation('Mt. Vinson', 16050)              :- peak('Mt. Vinson').
elevation('The Puncak Jaya', 16023)         :- peak('The Puncak Jaya').

% Facts about climbers
climber('John').
climber('Kelly').
climber('Maria').
climber('Derek').
climber('Thyago').

% Facts about certifications
certified('John')   :- climber('John').
certified('Kelly')  :- climber('Kelly').
certified('Maria')  :- climber('Maria').
certified('Derek')  :- climber('Derek').

% Rules about which mountains climbers will climb
would_climb('John', M) :-
    certified('John'),
    location(M,'North America').
would_climb('Kelly', M) :-
    certified('Kelly'),
    elevation(M, E),E>=20000.
would_climb('Maria', M) :-  
    certified('Maria'),
    peak(M).
would_climb('Derek', M) :-
    certified('Derek'),
    (location(M,'Europe') ; location(M,'Africa') ; location(M,'Australia')),
    elevation(M,E),E=<19000.