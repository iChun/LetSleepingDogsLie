package me.ichun.letsleepingdogslie.client.model;

import me.ichun.letsleepingdogslie.client.core.EventHandler;
import me.ichun.letsleepingdogslie.common.LetSleepingDogsLie;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;

public class WolfModel<T extends WolfEntity> extends net.minecraft.client.renderer.entity.model.WolfModel<T>
{
    public WolfModel()
    {
        super();

        this.mane.cubeList.clear();
        this.mane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
        this.mane.setRotationPoint(0.0F, 14.0F, 2.0F);

        this.legBackRight.cubeList.clear();
        this.legBackRight.setRotationPoint(-1.5F, 23.0F, 7.0F);
        this.legBackRight.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);

        this.legBackLeft.cubeList.clear();
        this.legBackLeft.setRotationPoint(1.5F, 23.0F, 7.0F);
        this.legBackLeft.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);

        this.legFrontRight.cubeList.clear();
        this.legFrontRight.setRotationPoint(-1.5F, 23.0F, -4.0F);
        this.legFrontRight.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);

        this.legFrontLeft.cubeList.clear();
        this.legFrontLeft.setRotationPoint(1.5F, 23.0F, -4.0F);
        this.legFrontLeft.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
    }

    @Override
    public void setLivingAnimations(WolfEntity entitywolf, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        if (entitywolf.func_233678_J__()) //isAngry()
        {
            this.tail.rotateAngleY = 0.0F;
        }
        else
        {
            this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        //RESET POSITIONS
        this.head.setRotationPoint(-1.0F, 13.5F, -7.0F);
        this.legBackRight.rotateAngleY = this.legBackLeft.rotateAngleY = this.legFrontRight.rotateAngleY = this.legFrontLeft.rotateAngleY = 0F;
        this.legBackRight.rotateAngleZ = this.legBackLeft.rotateAngleZ = this.legFrontRight.rotateAngleZ = this.legFrontLeft.rotateAngleZ = 0F;
        this.mane.rotateAngleY = this.body.rotateAngleY = 0F;
        this.mane.rotateAngleZ = this.body.rotateAngleZ = 0F;
        this.tailChild.rotateAngleZ = 0F;
        //END RESET POSITIONS

        if (entitywolf.func_233684_eK_()) //isSitting
        {
            EventHandler.WolfInfo info = LetSleepingDogsLie.eventHandler.getWolfInfo(entitywolf);
            if(info.isLying())
            {
                float halfPi = ((float)Math.PI / 2F);

                String[] poses = info.getCompatiblePoses(entitywolf);

                //mane flat; set the front
                this.mane.setRotationPoint(0.0F, 20.9F, -3.0F);
                this.mane.rotateAngleX = halfPi;
                this.mane.rotateAngleY = 0.0F;

                if(entitywolf.getName().getUnformattedComponentText().equals("iChun"))
                {
                    this.body.rotateAngleY = this.mane.rotateAngleY = ((entitywolf.ticksExisted + partialTickTime) / 3.5F);
                    this.body.rotateAngleZ = this.mane.rotateAngleZ = ((entitywolf.ticksExisted + partialTickTime) / 3.5F);

                    this.legBackRight.rotateAngleY = this.legFrontRight.rotateAngleY = ((entitywolf.ticksExisted + partialTickTime) / 3.5F);
                    this.legBackLeft.rotateAngleY = this.legFrontLeft.rotateAngleY = -((entitywolf.ticksExisted + partialTickTime) / 3.5F);
                    this.legBackRight.rotateAngleZ = this.legFrontRight.rotateAngleZ = ((entitywolf.ticksExisted + partialTickTime) / 5F);
                    this.legBackLeft.rotateAngleZ = this.legFrontLeft.rotateAngleZ = -((entitywolf.ticksExisted + partialTickTime) / 5F);
                }

                switch(poses[0]) //front
                {
                    case "forelegSideL":
                    {
                        this.mane.setRotationPoint(0.50F, 20.9F, -3.0F);
                        this.mane.rotateAngleX = halfPi;
                        this.mane.rotateAngleZ = -0.956091F;

                        this.head.setRotationPoint(-1.0F, 20.50F, -7.0F);

                        this.legFrontRight.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.legFrontRight.rotateAngleX = -0.087266F;
                        this.legFrontRight.rotateAngleZ = -halfPi;

                        this.legFrontLeft.setRotationPoint(1.5F, 21.0F, -3.0F);
                        this.legFrontLeft.rotateAngleX = 0.10472F;
                        this.legFrontLeft.rotateAngleZ = -1.320342F;
                        break;
                    }
                    case "forelegSideR":
                    {
                        this.mane.setRotationPoint(-0.50F, 20.9F, -3.0F);
                        this.mane.rotateAngleX = halfPi;
                        this.mane.rotateAngleZ = 0.956091F;

                        this.head.setRotationPoint(-1.0F, 20.50F, -7.0F);

                        this.legFrontRight.setRotationPoint(-1.5F, 21.0F, -3.0F);
                        this.legFrontRight.rotateAngleX = 0.10472F;
                        this.legFrontRight.rotateAngleZ = 1.320342F;

                        this.legFrontLeft.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.legFrontLeft.rotateAngleX = -0.087266F;
                        this.legFrontLeft.rotateAngleZ = halfPi;
                        break;
                    }
                    case "forelegStraight":
                    {
                        this.head.setRotationPoint(-1.0F, 19.0F, -7.0F);

                        this.legFrontRight.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.legFrontRight.rotateAngleX = -halfPi;

                        this.legFrontLeft.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.legFrontLeft.rotateAngleX = -halfPi;
                        break;
                    }
                    case "forelegSprawled":
                    {
                        this.head.setRotationPoint(-1.0F, 20.4F, -7.0F);

                        this.legFrontRight.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.legFrontRight.rotateAngleX = -halfPi;
                        this.legFrontRight.rotateAngleY = 0.63739424F;

                        this.legFrontLeft.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.legFrontLeft.rotateAngleX = -halfPi;
                        this.legFrontLeft.rotateAngleY = -0.63739424F;
                        break;
                    }
                    case "forelegSprawledBack":
                    {
                        this.head.setRotationPoint(-1.0F, 21F, -7.0F);

                        this.legFrontRight.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.legFrontRight.rotateAngleX = halfPi;
                        this.legFrontRight.rotateAngleY = -0.63739424F;

                        this.legFrontLeft.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.legFrontLeft.rotateAngleX = halfPi;
                        this.legFrontLeft.rotateAngleY = 0.63739424F;
                        break;
                    }
                    case "forelegSkewedL":
                    {
                        this.head.setRotationPoint(-1.0F, 20.0F, -7.0F);

                        this.legFrontRight.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.legFrontRight.rotateAngleX = -halfPi;
                        this.legFrontRight.rotateAngleY = -0.436332F;

                        this.legFrontLeft.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.legFrontLeft.rotateAngleX = -halfPi;
                        this.legFrontLeft.rotateAngleY = -0.349066F;
                        break;
                    }
                    case "forelegSkewedR":
                    {
                        this.head.setRotationPoint(-1.0F, 20.0F, -7.0F);

                        this.legFrontRight.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.legFrontRight.rotateAngleX = -halfPi;
                        this.legFrontRight.rotateAngleY = 0.349066F;

                        this.legFrontLeft.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.legFrontLeft.rotateAngleX = -halfPi;
                        this.legFrontLeft.rotateAngleY = 0.436332F;
                        break;
                    }
                    default:{}
                }

                //body flat; set the back
                this.body.setRotationPoint(0.0F, 20.9F, 2.0F);
                this.body.rotateAngleX = halfPi;

                this.tail.setRotationPoint(-1.0F, 19.0F, 8.0F);

                switch(poses[1])
                {
                    case "hindlegSideL":
                    {
                        this.body.setRotationPoint(0.0F, 20.9F, 2.0F);
                        this.body.rotateAngleX = halfPi;
                        this.body.rotateAngleZ = -0.610865F;

                        this.tail.setRotationPoint(-2.0F, 19.8F, 8.0F);
                        this.tailChild.rotateAngleZ = -0.610865F;

                        this.legBackRight.setRotationPoint(-0.2F, 23.5F, 6.5F);
                        this.legBackRight.rotateAngleX = -halfPi;
                        this.legBackRight.rotateAngleY = -0.956091F;

                        this.legBackLeft.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.legBackLeft.rotateAngleX = -halfPi;
                        this.legBackLeft.rotateAngleY = -1.365895F;
                        break;
                    }
                    case "hindlegSideR":
                    {
                        this.body.setRotationPoint(0.0F, 20.9F, 2.0F);
                        this.body.rotateAngleX = halfPi;
                        this.body.rotateAngleZ = 0.610865F;

                        this.tail.setRotationPoint(0.0F, 19.8F, 8.0F);
                        this.tailChild.rotateAngleZ = 0.610865F;

                        this.legBackRight.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.legBackRight.rotateAngleX = -halfPi;
                        this.legBackRight.rotateAngleY = 1.365895F;

                        this.legBackLeft.setRotationPoint(0.2F, 23.5F, 6.5F);
                        this.legBackLeft.rotateAngleX = -halfPi;
                        this.legBackLeft.rotateAngleY = 0.956091F;
                        break;
                    }
                    case "hindlegStraight":
                    {
                        this.legBackRight.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.legBackRight.rotateAngleX = -halfPi;

                        this.legBackLeft.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.legBackLeft.rotateAngleX = -halfPi;
                        break;
                    }
                    case "hindlegStraightBack":
                    {
                        this.legBackRight.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.legBackRight.rotateAngleX = halfPi;

                        this.legBackLeft.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.legBackLeft.rotateAngleX = halfPi;
                        break;
                    }
                    case "hindlegSprawled":
                    {
                        this.legBackRight.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.legBackRight.rotateAngleX = -halfPi;
                        this.legBackRight.rotateAngleY = 0.523599F;

                        this.legBackLeft.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.legBackLeft.rotateAngleX = -halfPi;
                        this.legBackLeft.rotateAngleY = -0.523599F;
                        break;
                    }
                    case "hindlegSprawledBack":
                    {
                        this.legBackRight.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.legBackRight.rotateAngleX = halfPi;
                        this.legBackRight.rotateAngleY = -0.523599F;

                        this.legBackLeft.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.legBackLeft.rotateAngleX = halfPi;
                        this.legBackLeft.rotateAngleY = 0.523599F;
                        break;
                    }
                }

                if(this.isChild)
                {
                    this.head.rotationPointY -= (this.head.rotationPointY - 13.5F) * 0.5F;
                }
            }
            else
            {
                this.mane.setRotationPoint(0.0F, 16.0F, -3.0F);
                this.mane.rotateAngleX = ((float)Math.PI * 2F / 5F);
                this.mane.rotateAngleY = 0.0F;
                this.body.setRotationPoint(0.0F, 18.0F, 0.0F);
                this.body.rotateAngleX = ((float)Math.PI / 4F);
                this.tail.setRotationPoint(-1.0F, 21.0F, 6.0F);
                this.legBackRight.setRotationPoint(-1.5F, 22.0F, 2.0F);
                this.legBackRight.rotateAngleX = ((float)Math.PI * 3F / 2F);
                this.legBackLeft.setRotationPoint(1.5F, 22.0F, 2.0F);
                this.legBackLeft.rotateAngleX = ((float)Math.PI * 3F / 2F);
                this.legFrontRight.rotateAngleX = 5.811947F;
                this.legFrontRight.setRotationPoint(-1.49F, 17.0F, -4.0F);
                this.legFrontLeft.rotateAngleX = 5.811947F;
                this.legFrontLeft.setRotationPoint(1.51F, 17.0F, -4.0F);


                this.mane.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.08F);
                this.body.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.16F);
                this.tailChild.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.2F);
            }
        }
        else
        {
            this.body.setRotationPoint(0.0F, 14.0F, 2.0F);
            this.body.rotateAngleX = ((float)Math.PI / 2F);
            this.mane.setRotationPoint(0.0F, 14.0F, -3.0F);
            this.mane.rotateAngleX = this.body.rotateAngleX;
            this.tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
            this.legBackRight.setRotationPoint(-1.5F, 16.0F, 7.0F);
            this.legBackLeft.setRotationPoint(1.5F, 16.0F, 7.0F);
            this.legFrontRight.setRotationPoint(-1.5F, 16.0F, -4.0F);
            this.legFrontLeft.setRotationPoint(1.5F, 16.0F, -4.0F);
            this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;


            this.mane.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.08F);
            this.body.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.16F);
            this.tailChild.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.2F);
        }

        this.headChild.rotateAngleZ = entitywolf.getInterestedAngle(partialTickTime) + entitywolf.getShakeAngle(partialTickTime, 0.0F);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.tail.rotateAngleX = ageInTicks; //full health = 1.7278761 ; 1 hp = 0.5340708

        if (LetSleepingDogsLie.eventHandler.getWolfInfo(entityIn).isLying())
        {
            this.tail.rotateAngleX *= 0.5796969225510672F;
        }
    }
}
