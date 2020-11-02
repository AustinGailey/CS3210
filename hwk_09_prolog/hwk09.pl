mountain('Mt. Everest','Asia',29029).
mountain('Aconcagua','South America',22841).
mountain('Mt. McKinley','North America',20312).
mountain('Kilimanjaro','Africa',19340).
mountain('Mt. Elbrus','Europe',18510).
mountain('Mt. Vinson','Antartica',16050).
mountain('Puncak Jaya','Australia',16023).
climber('John').
climber('Kelly').
climber('Maria').
climber('Derek').
climber('Thyago').
certified('John').
certified('Kelly').
certified('Maria').
certified('Derek').
would_climb('John') :-
    certified('John'),
    mountain(Name,Continent,_),
    Continent == 'North America',
    write(Name).  
would_climb('Kelly') :-
    certified('Kelly'),
    mountain(Name,_,Elevation),
    Elevation>=20000,
    write(Name).
would_climb('Maria') :-
    certified('Maria'),
    mountain(Name,_,_),
    write(Name).
would_climb('Derek') :-
    certified('Derek'),
    mountain(Name,Continent,Elevation),
    (Continent == 'Europe'
    ;Continent == 'Africa'
    ;Continent == 'Australia'),
    Elevation=<19000,
    write(Name).