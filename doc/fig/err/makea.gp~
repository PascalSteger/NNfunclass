set terminal postscript eps
set output "e1.eps"
set size 2.5/5., 1.5/3.
#set style line 5 lt rgb "red" lw 3

unset key
unset xlabel
unset ylabel
#unset ytics
#plot [-3.14:3.14] sin(x)
plot [0:30] [0:12]"e1.txt" using ($1) with lines,\
     "e1.txt" using ($2) with lines,\
     "e1.txt" using ($3) with lines,\
     "e1.txt" using ($4) with lines,\
     "e2.txt" using ($1+$2+$3+$4)/4 pt 6 lt rgb "red"