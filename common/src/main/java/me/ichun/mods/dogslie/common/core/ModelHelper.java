package me.ichun.mods.dogslie.common.core;

import net.minecraft.Util;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class ModelHelper
{
    public static final HashMap<String, Pos> BASE_POS = Util.make(new HashMap<>(), m -> {
        //Taken from our model override in WolfModelMixin
        //Front
        m.put("head", new Pos(-1.0F, 13.5F, -7.0F));
        m.put("upper_body", new Pos(0.0F, 14.0F, -3.0F));
        m.put("right_front_leg", new Pos(-1.5F, 16.0F, -4.0F));
        m.put("left_front_leg", new Pos(1.5F, 16.0F, -4.0F));

        //Back
        m.put("tail", new Pos(-1.0F, 12.0F, 8.0F));
        m.put("body", new Pos(0.0F, 14.0F, 2.0F));
        m.put("right_hind_leg", new Pos(-1.5F, 16.0F, 7.0F));
        m.put("left_hind_leg", new Pos(1.5F, 16.0F, 7.0F));
    });
    private static final HashMap<String, Transformation> TRANSFORMATIONS = Util.make(new HashMap<>(), m -> {
        float halfPi = ((float)Math.PI / 2F);

        //Front
        m.put("forelegSideL", new Transformation(true,
            new PosRot(-1.0F, 20.50F, -7.0F),
            new PosRot(0.50F, 20.9F, -3.0F, halfPi, null, -0.956091F),
            new PosRot(-1.5F, 23.0F, -4.0F, -0.087266F, null, -halfPi),
            new PosRot(1.5F, 21.0F, -3.0F, 0.10472F, null, -1.320342F)
        ));
        m.put("forelegSideR", new Transformation(true,
            new PosRot(-1.0F, 20.50F, -7.0F),
            new PosRot(-0.50F, 20.9F, -3.0F, halfPi, null, 0.956091F),
            new PosRot(-1.5F, 21.0F, -3.0F, 0.10472F, null, 1.320342F),
            new PosRot(1.5F, 23.0F, -4.0F, -0.087266F, null, halfPi)
        ));
        m.put("forelegStraight", new Transformation(true,
            new PosRot(-1.0F, 19.0F, -7.0F),
            new PosRot(0.0F, 20.9F, -3.0F, halfPi, 0F, null),
            new PosRot(-1.5F, 23.0F, -4.0F, -halfPi, null, null),
            new PosRot(1.5F, 23.0F, -4.0F, -halfPi, null, null)
        ));
        m.put("forelegSprawled", new Transformation(true,
            new PosRot(-1.0F, 20.4F, -7.0F),
            new PosRot(0.0F, 20.9F, -3.0F, halfPi, 0.0F, null),
            new PosRot(-1.5F, 23.0F, -4.0F, -halfPi, 0.63739424F, null),
            new PosRot(1.5F, 23.0F, -4.0F, -halfPi, -0.63739424F, null)
        ));
        m.put("forelegSprawledBack", new Transformation(true,
            new PosRot(-1.0F, 21F, -7.0F),
            new PosRot(0.0F, 20.9F, -3.0F, halfPi, 0.0F, null),
            new PosRot(-1.5F, 23.0F, -4.0F, halfPi, -0.63739424F, null),
            new PosRot(1.5F, 23.0F, -4.0F, halfPi, 0.63739424F, null)
        ));
        m.put("forelegSkewedL", new Transformation(true,
            new PosRot(-1.0F, 20.0F, -7.0F),
            new PosRot(0.0F, 20.9F, -3.0F, halfPi, 0.0F, null),
            new PosRot(-1.5F, 23.0F, -4.0F, -halfPi, -0.436332F, null),
            new PosRot(1.5F, 23.0F, -4.0F, -halfPi, -0.349066F, null)
        ));
        m.put("forelegSkewedR", new Transformation(true,
            new PosRot(-1.0F, 20.0F, -7.0F),
            new PosRot(0.0F, 20.9F, -3.0F, halfPi, 0.0F, null),
            new PosRot(-1.5F, 23.0F, -4.0F, -halfPi, 0.349066F, null),
            new PosRot(1.5F, 23.0F, -4.0F, -halfPi, 0.436332F, null)
        ));

        //Back
        m.put("hindlegSideL", new Transformation(false,
            new PosRot(-2.0F, 19.8F, 8.0F, null, null, -0.610865F),
            new PosRot(0.0F, 20.9F, 2.0F, halfPi, null, -0.610865F),
            new PosRot(-0.2F, 23.5F, 6.5F, -halfPi, -0.956091F, null),
            new PosRot(1.5F, 23.0F, 7.0F, -halfPi, -1.365895F, null)
        ));
        m.put("hindlegSideR", new Transformation(false,
            new PosRot(0.0F, 19.8F, 8.0F, null, null, 0.610865F),
            new PosRot(0.0F, 20.9F, 2.0F, halfPi, null, 0.610865F),
            new PosRot(-1.5F, 23.0F, 7.0F, -halfPi, 1.365895F, null),
            new PosRot(0.2F, 23.5F, 6.5F, -halfPi, 0.956091F, null)
        ));
        m.put("hindlegStraight", new Transformation(false,
            new PosRot(-1.0F, 19.0F, 8.0F),
            new PosRot(0.0F, 20.9F, 2.0F, halfPi, null, null),
            new PosRot(-1.5F, 23.0F, 7.0F, -halfPi, null, null),
            new PosRot(1.5F, 23.0F, 7.0F, -halfPi, null, null)
        ));
        m.put("hindlegStraightBack", new Transformation(false,
            new PosRot(-1.0F, 19.0F, 8.0F),
            new PosRot(0.0F, 20.9F, 2.0F, halfPi, null, null),
            new PosRot(-1.5F, 23.0F, 7.0F, halfPi, null, null),
            new PosRot(1.5F, 23.0F, 7.0F, halfPi, null, null)
        ));
        m.put("hindlegSprawled", new Transformation(false,
            new PosRot(-1.0F, 19.0F, 8.0F),
            new PosRot(0.0F, 20.9F, 2.0F, halfPi, null, null),
            new PosRot(-1.5F, 23.0F, 7.0F, -halfPi, 0.523599F, null),
            new PosRot(1.5F, 23.0F, 7.0F, -halfPi, -0.523599F, null)
        ));
        m.put("hindlegSprawledBack", new Transformation(false,
            new PosRot(-1.0F, 19.0F, 8.0F),
            new PosRot(0.0F, 20.9F, 2.0F, halfPi, null, null),
            new PosRot(-1.5F, 23.0F, 7.0F, halfPi, -0.523599F, null),
            new PosRot(1.5F, 23.0F, 7.0F, halfPi, 0.523599F, null)
        ));
    });

    public static void transformModel(String[] poses, ModelPart head, ModelPart upperBody, ModelPart rightFrontLeg, ModelPart leftFrontLeg, ModelPart tail, ModelPart body, ModelPart rightHindLeg, ModelPart legHindLeg, float ageScale)
    {
        Transformation frontTransformation = TRANSFORMATIONS.get(poses[0]);
        Transformation backTransformation = TRANSFORMATIONS.get(poses[1]);
        if(frontTransformation == null || backTransformation == null)
        {
            throw new RuntimeException("Cannot find transformation for pose: " + poses[0]);
        }

        frontTransformation.transform(head, upperBody, rightFrontLeg, leftFrontLeg, ageScale);
        backTransformation.transform(tail, body, rightHindLeg, legHindLeg, ageScale);
    }

    private static class Transformation
    {
        private final PosRot transformHeadOrTail; // head            /tail
        private final PosRot transformBody;       // upper_body      /body
        private final PosRot transformRightLeg;   // right_front_leg /right_hind_leg
        private final PosRot transformLeftLeg;    // left_front_leg  /left_hind_leg

        private Transformation(boolean front, PosRot headOrTail, PosRot body, PosRot rightLeg, PosRot leftLeg)
        {
            if(front)
            {
                transformHeadOrTail = headOrTail.minus(BASE_POS.get("head"));
                transformBody = body.minus(BASE_POS.get("upper_body"));
                transformRightLeg = rightLeg.minus(BASE_POS.get("right_front_leg"));
                transformLeftLeg = leftLeg.minus(BASE_POS.get("left_front_leg"));
            }
            else
            {
                transformHeadOrTail = headOrTail.minus(BASE_POS.get("tail"));
                transformBody = body.minus(BASE_POS.get("body"));
                transformRightLeg = rightLeg.minus(BASE_POS.get("right_hind_leg"));
                transformLeftLeg = leftLeg.minus(BASE_POS.get("left_hind_leg"));
            }
        }

        private void transform(ModelPart headOrTail, ModelPart body, ModelPart rightLeg, ModelPart leftLeg, float ageScale)
        {
            applyTransformation(headOrTail, transformHeadOrTail, ageScale);
            applyTransformation(body, transformBody, ageScale);
            applyTransformation(rightLeg, transformRightLeg, ageScale);
            applyTransformation(leftLeg, transformLeftLeg, ageScale);
        }

        private void applyTransformation(ModelPart part, PosRot transformation, float ageScale)
        {
            part.x += transformation.pos().x() * ageScale;
            part.y += transformation.pos().y() * ageScale;
            part.z += transformation.pos().z() * ageScale;

            applyRot(part, transformation.rot());
        }

        private void applyRot(ModelPart part, @Nullable Rot rot)
        {
            if(rot != null)
            {
                if(rot.x() != null) part.xRot = rot.x();
                if(rot.y() != null) part.yRot = rot.y();
                if(rot.z() != null) part.zRot = rot.z();
            }
        }
    }

    private record Pos(float x, float y, float z)
    {
        public Pos minus(Pos pos)
        {
            return new Pos(x - pos.x, y - pos.y, z - pos.z);
        }
    }
    private record Rot(@Nullable Float x, @Nullable Float y, @Nullable Float z){} // I use null to denote no rotation, hate me if you must.
    private record PosRot(Pos pos, @Nullable Rot rot){
        public PosRot(float x, float y, float z)
        {
            this(new Pos(x, y, z), null);
        }

        public PosRot(float x, float y, float z, Float rotX, Float rotY, Float rotZ)
        {
            this(new Pos(x, y, z), new Rot(rotX, rotY, rotZ));
        }

        public PosRot minus(Pos pos)
        {
            return new PosRot(this.pos.minus(pos), this.rot);
        }
    }
}
