--Adam Cunningham
--Comparative language
--assignment 7
--4/28
--part D, peano

data Nat =
        Zero |
        Succ Nat
        deriving Show

--converts from an Int to a peano number
toPeano :: Int -> Nat
toPeano 0 = Zero
toPeano n = Succ (toPeano (n-1))

--converts from a peano to an int
fromPeano :: Nat -> Int
fromPeano Zero = 0
fromPeano (Succ n) = 1 + (fromPeano n)

--adds peano numbers
addPeano :: Nat -> Nat -> Nat
addPeano Zero Zero = Zero
addPeano x Zero = x
addPeano x (Succ y) = addPeano (Succ x) y

--multiply peano numbers
mulPeano :: Nat -> Nat -> Nat
mulPeano (Succ Zero) y = y
mulPeano x (Succ Zero) = x
mulPeano Zero y = Zero
mulPeano x Zero = Zero
mulPeano x (Succ y) = addPeano x (mulPeano x y)

--determines equality for peano numbers
peanoEQ :: Nat -> Nat -> Bool
peanoEQ Zero Zero = True
peanoEQ (Succ x) Zero = False
peanoEQ Zero (Succ y) = False
peanoEQ (Succ x) (Succ y) = peanoEQ x y

--determines if for peano numbers x,y x<y
peanoLT :: Nat -> Nat -> Bool
peanoLT Zero Zero = False
peanoLT x Zero = False
peanoLT Zero y = True
peanoLT (Succ x) (Succ y) = peanoLT x y




