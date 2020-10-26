-- CS3210 - Principles of Programming Languages - Fall 2020
-- Homework 08 - A Simple Phonebook Application
-- Student Name∷

type Entry = (Person, Number)
type Person = String
type Number = Integer
type Phonebook = [Entry]

-- TODOd #1: define a function called add that takes a phonebook and an entry and returns a modified phonebook with the new entry IF the entry is not in phonebook already.
add :: Phonebook -> Entry -> Phonebook
add ph entry
  | notElem entry ph = ph ++ [entry]
  | otherwise = ph

-- TODOd #2: define a function called find that takes a phonebook and a person and returns a list of the entries in phonebook that have the person’s name.
find :: Phonebook -> Person -> Phonebook
find ph name = filter ((==name) . fst) ph

-- TODOd #3: define a function called delete that takes a phonebook and a person and returns a modified phonebook without the entries that have the person’s name.
delete :: Phonebook -> Person -> Phonebook
delete ph name = filter ((/=name) . fst) ph

main = do
  let pb = [ ("Joe Hughes", 4371239212), ("Mary Owen", 2039183421), ("Patty Riley", 2012349283), ("Mark Flores", 3039343844) ]
  print (pb)
  let pb2 = add pb ("Mark Flores", 1112221234)
  print (pb2) -- Mark Flores should have two entries now!
  let pb3 = add pb2 ("Mark Flores", 3039343844)
  print (pb3) -- Mark Flores should not be added!
  print (find pb3 "Mark Flores")
  let pb4 = delete pb3 "Mark Flores"
  print (pb4) -- all Mark Flores entries should be gone!
