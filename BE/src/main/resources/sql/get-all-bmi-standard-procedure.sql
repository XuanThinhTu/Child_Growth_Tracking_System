CREATE PROCEDURE GetAllBmiStandards()
BEGIN
SELECT
    id,
    gender,
    period,
    period_type,

    weight_neg4sd,
    weight_neg3sd,
    weight_neg2sd,
    weight_neg1sd,
    weight_median,
    weight_pos1sd,
    weight_pos2sd,
    weight_pos3sd,
    weight_pos4sd,

    height_neg4sd,
    height_neg3sd,
    height_neg2sd,
    height_neg1sd,
    height_median,
    height_pos1sd,
    height_pos2sd,
    height_pos3sd,
    height_pos4sd,

    bmi_neg4sd,
    bmi_neg3sd,
    bmi_neg2sd,
    bmi_neg1sd,
    bmi_median,
    bmi_pos1sd,
    bmi_pos2sd,
    bmi_pos3sd,
    bmi_pos4sd,

    head_circumference_neg4sd,
    head_circumference_neg3sd,
    head_circumference_neg2sd,
    head_circumference_neg1sd,
    head_circumference_median,
    head_circumference_pos1sd,
    head_circumference_pos2sd,
    head_circumference_pos3sd,
    head_circumference_pos4sd
    FROM bmi_standards;
END