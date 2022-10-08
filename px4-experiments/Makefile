reinstall-dependencies:
	mvn dependency:purge-local-repository

update-dependencies:
	mvn dependency:resolve
	mvn install

organ1:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.OrganDeliveryMain"

organ2env:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.organ_delivery.it2.env_model.Dpsl"

tmgp:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.terrain_map_ground_prox.TmgpTest"

tmgpMain:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.terrain_map_ground_prox.TmgpMain"

egofollower: egofollower-multithread
	# mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multithreaded_ego_follower.EgoFollowerDroneMultithreaded"
	# mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multithreaded_ego_follower.EgoFollowerDroneMultithreaded"

egofollower-singlethread:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.singlethreaded_ego_follower.EgoFollowerSinglethreaded"

egofollower-multiprocess:
	cd scripts && ./compile_ego_follower.sh
	# alternatively, you can use the following command to compile the code:
	# mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower.EgoDrone"
	# mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower.FollowerDrone"

egofollower-multithread:
	# this is for a simple demo purpose
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multithreaded_ego_follower.EgoFollowerDroneMultithreaded"

bound:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.boundary.BoundaryEnforcer"
	
spiral:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.fly_spiral.FlySpiral"


simulate:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.Simulator"


main-arg:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.BoundaryRunawayMain" -Dexec.args="simplearg"


model-test:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.model.ModelTest"

resolver:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.resolver.Resolver"

main2:	
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.Main"

configparser:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.utils.ConfigParser"

robust:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor.RobustnessCalculator"

main:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.BoundaryRunawayMain" \
	-Dexec.args="experiments/config/bound_run/randomized_config/config_188.ini" # -Dexec.args="experiments/config/bound_run/default_config.ini"
	echo "Done"

generate_boundary_runaway_params:
	cd experiments/config/bound_run/ && python3 config_randomizer.py

organ:
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.organ_delivery.main.OrganDeliveryMain" \
	-Dexec.args="experiments/config/organ_delivery/randomized_config_new/config_2.ini" \


model-test2:
	# mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.organ_delivery.main.speed_battery_model.ModelTest"
	mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.organ_delivery.main.updated_model.ModelTest"