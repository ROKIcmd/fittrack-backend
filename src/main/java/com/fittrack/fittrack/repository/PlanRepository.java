package com.fittrack.fittrack.repository;

import com.fittrack.fittrack.model.DietPlan;
import com.fittrack.fittrack.model.FitnessPlan;
import com.fittrack.fittrack.model.WorkoutPlan;
import java.util.List;

public class PlanRepository extends FileRepository<FitnessPlan> {

    public PlanRepository() {
        super("data/plans.dat", FitnessPlan::getId);
    }

    public List<WorkoutPlan> findWorkoutPlansByMember(String memberId) {
        return findAll().stream()
                .filter(p -> p instanceof WorkoutPlan && memberId.equals(p.getMemberId()))
                .map(p -> (WorkoutPlan) p)
                .toList();
    }

    public List<DietPlan> findDietPlansByMember(String memberId) {
        return findAll().stream()
                .filter(p -> p instanceof DietPlan && memberId.equals(p.getMemberId()))
                .map(p -> (DietPlan) p)
                .toList();
    }
}