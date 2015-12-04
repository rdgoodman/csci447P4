This is a Maven project.

Procedure for getting Maven up and running (in Eclipse):
	1) Pull from repository
	2) Check for m2e plugin (Help -> Installation Details -> Plug-Ins, search "maven")
	3) Check for updates (Help -> Check for Updates)
	4) Set project to use JDK version 1.8 (Window -> Preferences -> Installed JREs)
	5) Right click on project and select Run As -> Maven Build. Set the goal to "compile" (no quotes)
	6) If no errors, then click RunModels -> Run As -> Java Application
	7) All should be well!
	
	
To do sample runs of an algorithm:
	1) Run RunModels.java
	2) Follow the prompts to select the algorithm you want
	
	* Note that, for the sake of readability, the sample runs are simplified problems
	(e.g. PSO only runs for 100 iterations instead of 1000)
	
	
Classes:

	ACO.java
		An ant-based clustering (ABC) instance
		
	ACOAgent.java
		Represents an ant in an ACO/ABC instance
	
	ANNTuningExperiment.java
		Used to run the competitive learning experiments in the paper
		
	Cluster.java
		Stores a single cluster object, i.e. a centroid (or equivalent) and a set of data points
		
	ClusterSet.java
		Stores the full output of a clustering algorithm, i.e. a set of Clusters
		
	CompetitiveANN.java
		A competitive learning neural network instance
		
	Datum.java
		Stores a single data point to be clustered
		
	DBScan.java
		A DBScan algorithm instance
		
	DBScanTuningExperiment.java
		Used to run tuning experiments for DBScan
		
	Distance.java
		Does the distance calculations for K Means
		
	KMeans.java
		A k-means algorithm instance
		
	KMeansTuningExperiment.java
		Used to run tuning experiments for K Means
	
	Neuron.java
		A single unit in a competitive learning neural network
		
	Particle.java
		A single particle in a PSO swarm
		
	PSO.java
		An instance of a clustering particle swarm optimizer
		
	PSOExperiment.java
		Used to run experiments for PSO
	
	RunModels.java
		Main class
	
