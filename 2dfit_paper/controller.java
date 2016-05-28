Controller

Input
has Array Position
has Array error
has Scale
has Translation
can Normalize
can generateMCCloud

MCCloud
has Array Position

Classificator
has Input
has MCCloud
has LinearSuperposition
can findFunctions

LinearSuperposition
has Array Function
can computeAt
can computeFitnessXLeastSquare
can computeFitnessXYLeastSquare

Function
has Form
has Scale
has Offset

Form
has Integer FunctionForm
has Integer FunctionDegree

Scale
has Integer xscale
has Integer yscale

Offset
has Integer xoffset
has Integer yoffset

findFunctions: main idea contracting to prototypes
can invokeFFT
can clusterFrequencies
can findMainFitNN
can findMainFitHN
can calculate 

findMainFitNN:
is findMainFit
has NeuralNetwork
implements learnFunction

findMainFitHN:
is findMainFit
has HolographicNeuron
implements learnFunction

findMainFitFlow:
is findMainFit
has Integer scale
implements learnFunction

findMainFitGen:
is findMainFit
has Array Gene
has Boolean Elite
has Probability Crossing
has Probability Mutation
implements learnFunction

findMainFit:
has Array FourierCoeffs
can normalize
can learnFunction
can findFitParameters
can AppendFunction

    NeuralNetwork:
has Array Neurons
has ConnectionMatrix
can backpropagate

    Neuron:
has Array Input
has double thresh_
has drain
can updateInternalState
can generateOutput

    HolographicNeuron:
has Array Input
has Eigenfrequency
has ActivationLevel

    Hopfield:
has Array Neurons
has connectionmatrix
has prototypes
has temp_
can converge

    GeneticProgram:
has population
has crossing rate
has mutation rate
has elitism
has Array basics
can performProgram

    Population:
has Vector[] Gene
can mutate
can cross

    Clustering:
has Vec[] points
