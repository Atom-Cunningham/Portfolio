{- -------------------------------------------------------------------------------------
   CSc 372, Spring 2020, Midterm II

   MY NAME      : <ENTER YOUR NAME HERE>

   MY UA NETID  : <ENTER YOUR NETID HERE>
-}

{- -------------------------------------------------------------------------------------
   Rules that apply during this exam:
      1) You can use the Haskell compiler (ghci) and a text editor to work the problems.
         You should edit this file to enter your answers. 
      2) You cannot use or search the Internet.
      3) You cannot communicate with anyone over phone, the Internet, email, in person, etc.
      4) You may have your phone next to you but you can only use it in an emergency.
      5) You should be aqs in your room when you take the exam.
      6) You should be logged into Zoom throughout the exam, with video turned on, audio turned off.
         We will provide 3 rooms:
            *) Last names starting with A-J, monitored by Christian: https://arizona.zoom.us/j/982017165
            *) Last names starting with K-R, monitored by Wenkang: https://arizona.zoom.us/j/318806041 
            *) Last names starting with S-Z, monitored by Claire: https://arizona.zoom.us/j/478386720
      7) This is a closed book exam: no notes, no books, etc.
      8) You may print out the exam. 
      9) You may have one blank piece of scratch paper and a pen/pencil.
     10) If you have questions, use Zoom chat to ask them. Keep in mind that everyone can see these
         chats, so don't write anything that reveals an answer.
     11) If you get knocked off the Internet during the exam, don't panic and spend 10 minutes
         figuring out what went wrong! Text me at 520-271-4960 to explain what happened and continue 
         taking the exam.
     12) When you're done, submit *this file* only.
           a) Primarily, upload to d2l. 
           b) If that doesn't work, email it to me (collberg@gmail.com).
           c) If that doesn't work, take a picture of this file with your phone and 
              text it to me at 520-271-4960. 
         Don't go over time!
     13) Before you submit this file, make sure that there are no syntax errors! We will both
         run your functions and view them manually.

   By submitting this file you certify that you followed the rules above. Even though it's easier
   to cheat under these circumstances, you are still bound by the university's and this course's
   codes of academic integrity.
-}


------------------------------------------------------------------------------------
-- Sum
------------------------------------------------------------------------------------

sum1 :: [Int] -> Int 
	 -- This is a placeholder for your answer!

sum1 xs =
	if xs == []
	then 0
	else (head xs) + sum1 (tail xs) 


------------------------------------------------------------------------------------

sum2 :: [Int] -> Int 
sum2 xs
	| xs == [] = 0
	| otherwise = (head xs) + sum2 (tail xs) 


------------------------------------------------------------------------------------

sum3 :: [Int] -> Int 
sum3 [] = 0 			 -- This is a placeholder for your answer!
sum3 (x:xs) = x + sum3 xs

------------------------------------------------------------------------------------
-- Range
------------------------------------------------------------------------------------
range1 :: Int -> Int -> [Int]
range1 f t =			 -- This is a placeholder for your answer!
	if f > t
	then []
	else f:(range1 (f+1) t)

------------------------------------------------------------------------------------

range2 :: Int -> Int -> [Int]
range2 f t			 -- This is a placeholder for your answer!
	| f > t = []
	| otherwise = f:(range1 (f+1) t)

------------------------------------------------------------------------------------
-- Strip
------------------------------------------------------------------------------------

-- strip3 ::          -- Give the signature of strip3 here!
strip3 :: Eq a => a -> [a] -> [a]
strip3 _ _ = []       -- Ignore this line, just to make this compile!


------------------------------------------------------------------------------------
-- Takeskip
------------------------------------------------------------------------------------
--
{-
exam.hs:106:11: error: parse error on input ‘(’
    |
106 |         | (fst (head xs)) == "skip" = takeskip (tail xs) (drop (snd (head xs)) ys)
    | 
	?????????~!
	
takeskip :: [(String,Int)] -> [a] -> [a]
takeskip [] [] = []			 -- This is a placeholder for your answer!
takeskip [] ys = []
takeskip xs ys
	| fst (head xs) == "take" = take (snd (head xs) ys ++ (takeskip (tail xs) (drop (snd (head xs)) ys))
	| (fst (head xs)) == "skip" = (takeskip (tail xs) (drop (snd (head xs))) ys)
	| otherwise = ys
-}
------------------------------------------------------------------------------------
-- Vecprod
------------------------------------------------------------------------------------

vecprod :: Num a => [a] -> [a] -> a
vecprod [] [] = 0			 -- This is a placeholder for your answer!
vecprod (x:xs) (y:ys)
	|length (x:xs) == length (y:ys) = error "unequal lengths"
	|otherwise = (x * y) + (vecprod xs ys)



------------------------------------------------------------------------------------
-- Collatz
------------------------------------------------------------------------------------

collatz :: Int -> [Int]
collatz n = collatzHelp [n] n			 -- This is a placeholder for your answer!
collatzHelp :: [Int] -> Int -> [Int]
collatzHelp xs n
	| n == 1 = xs
	| even n = collatzHelp (xs ++ [(n `div` 2)]) (n `div`2)
	| otherwise = collatzHelp (xs ++ [(n*3 +1)]) (n*3 + 1)

------------------------------------------------------------------------------------
-- Split
------------------------------------------------------------------------------------
{-
split :: (Eq a,Ord a) => a -> [a] -> ([a],[a],[a])
split x [] = ([],[],[])
split x xs = splitHelp x [] [] [] xs			 -- This is a placeholder for your answer!
splitHelp :: (Eq a,Ord a) => a -> [a] -> [a] -> [a] -> [a] -> ([a],[a],[a])
splitHelp x qs ws es [] = (qs, ws, es)
splitHelp x qs ws es (y:ys)
	| x == y = splitHelp x qs y:ws es ys
	| y < x = splitHelp x y:qs ws es ys
	| otherwise = splitHelp x qs ws y:es ys
-}
------------------------------------------------------------------------------------
-- Frequency
------------------------------------------------------------------------------------

freq :: [Integer] -> [(Integer,Int)]
freq [] = []			 -- This is a placeholder for your answer!
freq x:xs = (freqHelper x 0 (x:xs)):(freq xs)
freqHelper :: Integer -> Integer -> (Integer,Int)
freqHelper n count (x:xs)
	| xs == [] = (n, count)
	| n == x = freqHelper n (count+1) xs
	| otherwise = freqHelper n count xs
