package me.ichun.letsleepingdogslie.common.model;

import me.ichun.letsleepingdogslie.common.LetSleepingDogsLie;
import me.ichun.letsleepingdogslie.common.core.TickHandlerClient;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.MathHelper;

public class ModelWolf extends net.minecraft.client.model.ModelWolf
{
    public ModelWolf()
    {
        super();

        this.wolfMane = new ModelRenderer(this, 21, 0);
        this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
        this.wolfMane.setRotationPoint(0.0F, 14.0F, 2.0F);

        this.wolfLeg1 = new ModelRenderer(this, 0, 18);
        this.wolfLeg1.setRotationPoint(-1.5F, 23.0F, 7.0F);
        this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);

        this.wolfLeg2 = new ModelRenderer(this, 0, 18);
        this.wolfLeg2.setRotationPoint(1.5F, 23.0F, 7.0F);
        this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);

        this.wolfLeg3 = new ModelRenderer(this, 0, 18);
        this.wolfLeg3.setRotationPoint(-1.5F, 23.0F, -4.0F);
        this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);

        this.wolfLeg4 = new ModelRenderer(this, 0, 18);
        this.wolfLeg4.setRotationPoint(1.5F, 23.0F, -4.0F);
        this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntityWolf entitywolf = (EntityWolf)entitylivingbaseIn;

        if (entitywolf.isAngry())
        {
            this.wolfTail.rotateAngleY = 0.0F;
        }
        else
        {
            this.wolfTail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        //RESET POSITIONS
        this.wolfHeadMain.setRotationPoint(-1.0F, 13.5F, -7.0F);
        this.wolfLeg1.rotateAngleY = this.wolfLeg2.rotateAngleY = this.wolfLeg3.rotateAngleY = this.wolfLeg4.rotateAngleY = 0F;
        this.wolfLeg1.rotateAngleZ = this.wolfLeg2.rotateAngleZ = this.wolfLeg3.rotateAngleZ = this.wolfLeg4.rotateAngleZ = 0F;
        this.wolfMane.rotateAngleY = this.wolfBody.rotateAngleY = 0F;
        this.wolfMane.rotateAngleZ = this.wolfBody.rotateAngleZ = 0F;
        this.wolfTail.rotateAngleZ = 0F;
        //END RESET POSITIONS

        if (entitywolf.isSitting())
        {
            TickHandlerClient.WolfInfo info = LetSleepingDogsLie.tickHandlerClient.getWolfInfo(entitywolf);
            if(info.isLying())
            {
                float halfPi = ((float)Math.PI / 2F);

                String[] poses = info.getCompatiblePoses(entitywolf);

                //mane flat; set the front
                this.wolfMane.setRotationPoint(0.0F, 20.9F, -3.0F);
                this.wolfMane.rotateAngleX = halfPi;
                this.wolfMane.rotateAngleY = 0.0F;

                if(entitywolf.getName().equals("iChun"))
                {
                    this.wolfBody.rotateAngleY = this.wolfMane.rotateAngleY = ((entitywolf.ticksExisted + partialTickTime) / 3.5F);
                    this.wolfBody.rotateAngleZ = this.wolfMane.rotateAngleZ = ((entitywolf.ticksExisted + partialTickTime) / 3.5F);

                    this.wolfLeg1.rotateAngleY = this.wolfLeg3.rotateAngleY = ((entitywolf.ticksExisted + partialTickTime) / 3.5F);
                    this.wolfLeg2.rotateAngleY = this.wolfLeg4.rotateAngleY = -((entitywolf.ticksExisted + partialTickTime) / 3.5F);
                    this.wolfLeg1.rotateAngleZ = this.wolfLeg3.rotateAngleZ = ((entitywolf.ticksExisted + partialTickTime) / 5F);
                    this.wolfLeg2.rotateAngleZ = this.wolfLeg4.rotateAngleZ = -((entitywolf.ticksExisted + partialTickTime) / 5F);
                }

                switch(poses[0]) //front
                {
                    case "forelegSideL":
                    {
                        this.wolfMane.setRotationPoint(0.50F, 20.9F, -3.0F);
                        this.wolfMane.rotateAngleX = halfPi;
                        this.wolfMane.rotateAngleZ = -0.956091F;

                        this.wolfHeadMain.setRotationPoint(-1.0F, 20.50F, -7.0F);

                        this.wolfLeg3.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.wolfLeg3.rotateAngleX = -0.087266F;
                        this.wolfLeg3.rotateAngleZ = -halfPi;

                        this.wolfLeg4.setRotationPoint(1.5F, 21.0F, -3.0F);
                        this.wolfLeg4.rotateAngleX = 0.10472F;
                        this.wolfLeg4.rotateAngleZ = -1.320342F;
                        break;
                    }
                    case "forelegSideR":
                    {
                        this.wolfMane.setRotationPoint(-0.50F, 20.9F, -3.0F);
                        this.wolfMane.rotateAngleX = halfPi;
                        this.wolfMane.rotateAngleZ = 0.956091F;

                        this.wolfHeadMain.setRotationPoint(-1.0F, 20.50F, -7.0F);

                        this.wolfLeg3.setRotationPoint(-1.5F, 21.0F, -3.0F);
                        this.wolfLeg3.rotateAngleX = 0.10472F;
                        this.wolfLeg3.rotateAngleZ = 1.320342F;

                        this.wolfLeg4.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.wolfLeg4.rotateAngleX = -0.087266F;
                        this.wolfLeg4.rotateAngleZ = halfPi;
                        break;
                    }
                    case "forelegStraight":
                    {
                        this.wolfHeadMain.setRotationPoint(-1.0F, 19.0F, -7.0F);

                        this.wolfLeg3.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.wolfLeg3.rotateAngleX = -halfPi;

                        this.wolfLeg4.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.wolfLeg4.rotateAngleX = -halfPi;
                        break;
                    }
                    case "forelegSprawled":
                    {
                        this.wolfHeadMain.setRotationPoint(-1.0F, 20.4F, -7.0F);

                        this.wolfLeg3.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.wolfLeg3.rotateAngleX = -halfPi;
                        this.wolfLeg3.rotateAngleY = 0.63739424F;

                        this.wolfLeg4.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.wolfLeg4.rotateAngleX = -halfPi;
                        this.wolfLeg4.rotateAngleY = -0.63739424F;
                        break;
                    }
                    case "forelegSprawledBack":
                    {
                        this.wolfHeadMain.setRotationPoint(-1.0F, 21F, -7.0F);

                        this.wolfLeg3.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.wolfLeg3.rotateAngleX = halfPi;
                        this.wolfLeg3.rotateAngleY = -0.63739424F;

                        this.wolfLeg4.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.wolfLeg4.rotateAngleX = halfPi;
                        this.wolfLeg4.rotateAngleY = 0.63739424F;
                        break;
                    }
                    case "forelegSkewedL":
                    {
                        this.wolfHeadMain.setRotationPoint(-1.0F, 20.0F, -7.0F);

                        this.wolfLeg3.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.wolfLeg3.rotateAngleX = -halfPi;
                        this.wolfLeg3.rotateAngleY = -0.436332F;

                        this.wolfLeg4.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.wolfLeg4.rotateAngleX = -halfPi;
                        this.wolfLeg4.rotateAngleY = -0.349066F;
                        break;
                    }
                    case "forelegSkewedR":
                    {
                        this.wolfHeadMain.setRotationPoint(-1.0F, 20.0F, -7.0F);

                        this.wolfLeg3.setRotationPoint(-1.5F, 23.0F, -4.0F);
                        this.wolfLeg3.rotateAngleX = -halfPi;
                        this.wolfLeg3.rotateAngleY = 0.349066F;

                        this.wolfLeg4.setRotationPoint(1.5F, 23.0F, -4.0F);
                        this.wolfLeg4.rotateAngleX = -halfPi;
                        this.wolfLeg4.rotateAngleY = 0.436332F;
                        break;
                    }
                    default:{}
                }

                //body flat; set the back
                this.wolfBody.setRotationPoint(0.0F, 20.9F, 2.0F);
                this.wolfBody.rotateAngleX = halfPi;

                this.wolfTail.setRotationPoint(-1.0F, 19.0F, 8.0F);

                switch(poses[1])
                {
                    case "hindlegSideL":
                    {
                        this.wolfBody.setRotationPoint(0.0F, 20.9F, 2.0F);
                        this.wolfBody.rotateAngleX = halfPi;
                        this.wolfBody.rotateAngleZ = -0.610865F;

                        this.wolfTail.setRotationPoint(-2.0F, 19.8F, 8.0F);
                        this.wolfTail.rotateAngleZ = -0.610865F;

                        this.wolfLeg1.setRotationPoint(-0.2F, 23.5F, 6.5F);
                        this.wolfLeg1.rotateAngleX = -halfPi;
                        this.wolfLeg1.rotateAngleY = -0.956091F;

                        this.wolfLeg2.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.wolfLeg2.rotateAngleX = -halfPi;
                        this.wolfLeg2.rotateAngleY = -1.365895F;
                        break;
                    }
                    case "hindlegSideR":
                    {
                        this.wolfBody.setRotationPoint(0.0F, 20.9F, 2.0F);
                        this.wolfBody.rotateAngleX = halfPi;
                        this.wolfBody.rotateAngleZ = 0.610865F;

                        this.wolfTail.setRotationPoint(0.0F, 19.8F, 8.0F);
                        this.wolfTail.rotateAngleZ = 0.610865F;

                        this.wolfLeg1.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.wolfLeg1.rotateAngleX = -halfPi;
                        this.wolfLeg1.rotateAngleY = 1.365895F;

                        this.wolfLeg2.setRotationPoint(0.2F, 23.5F, 6.5F);
                        this.wolfLeg2.rotateAngleX = -halfPi;
                        this.wolfLeg2.rotateAngleY = 0.956091F;
                        break;
                    }
                    case "hindlegStraight":
                    {
                        this.wolfLeg1.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.wolfLeg1.rotateAngleX = -halfPi;

                        this.wolfLeg2.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.wolfLeg2.rotateAngleX = -halfPi;
                        break;
                    }
                    case "hindlegStraightBack":
                    {
                        this.wolfLeg1.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.wolfLeg1.rotateAngleX = halfPi;

                        this.wolfLeg2.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.wolfLeg2.rotateAngleX = halfPi;
                        break;
                    }
                    case "hindlegSprawled":
                    {
                        this.wolfLeg1.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.wolfLeg1.rotateAngleX = -halfPi;
                        this.wolfLeg1.rotateAngleY = 0.523599F;

                        this.wolfLeg2.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.wolfLeg2.rotateAngleX = -halfPi;
                        this.wolfLeg2.rotateAngleY = -0.523599F;
                        break;
                    }
                    case "hindlegSprawledBack":
                    {
                        this.wolfLeg1.setRotationPoint(-1.5F, 23.0F, 7.0F);
                        this.wolfLeg1.rotateAngleX = halfPi;
                        this.wolfLeg1.rotateAngleY = -0.523599F;

                        this.wolfLeg2.setRotationPoint(1.5F, 23.0F, 7.0F);
                        this.wolfLeg2.rotateAngleX = halfPi;
                        this.wolfLeg2.rotateAngleY = 0.523599F;
                        break;
                    }
                }

                if(this.isChild)
                {
                    this.wolfHeadMain.rotationPointY -= (this.wolfHeadMain.rotationPointY - 13.5F) * 0.5F;
                }
            }
            else
            {
                this.wolfMane.setRotationPoint(0.0F, 16.0F, -3.0F);
                this.wolfMane.rotateAngleX = ((float)Math.PI * 2F / 5F);
                this.wolfMane.rotateAngleY = 0.0F;
                this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
                this.wolfBody.rotateAngleX = ((float)Math.PI / 4F);
                this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
                this.wolfLeg1.setRotationPoint(-1.5F, 22.0F, 2.0F);
                this.wolfLeg1.rotateAngleX = ((float)Math.PI * 3F / 2F);
                this.wolfLeg2.setRotationPoint(1.5F, 22.0F, 2.0F);
                this.wolfLeg2.rotateAngleX = ((float)Math.PI * 3F / 2F);
                this.wolfLeg3.rotateAngleX = 5.811947F;
                this.wolfLeg3.setRotationPoint(-1.49F, 17.0F, -4.0F);
                this.wolfLeg4.rotateAngleX = 5.811947F;
                this.wolfLeg4.setRotationPoint(1.51F, 17.0F, -4.0F);


                this.wolfMane.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.08F);
                this.wolfBody.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.16F);
                this.wolfTail.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.2F);
            }
        }
        else
        {
            this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
            this.wolfBody.rotateAngleX = ((float)Math.PI / 2F);
            this.wolfMane.setRotationPoint(0.0F, 14.0F, -3.0F);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
            this.wolfLeg1.setRotationPoint(-1.5F, 16.0F, 7.0F);
            this.wolfLeg2.setRotationPoint(1.5F, 16.0F, 7.0F);
            this.wolfLeg3.setRotationPoint(-1.5F, 16.0F, -4.0F);
            this.wolfLeg4.setRotationPoint(1.5F, 16.0F, -4.0F);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;


            this.wolfMane.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.08F);
            this.wolfBody.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.16F);
            this.wolfTail.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.2F);
        }

        this.wolfHeadMain.rotateAngleZ = entitywolf.getInterestedAngle(partialTickTime) + entitywolf.getShakeAngle(partialTickTime, 0.0F);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.wolfHeadMain.rotateAngleX = headPitch * 0.017453292F;
        this.wolfHeadMain.rotateAngleY = netHeadYaw * 0.017453292F;
        this.wolfTail.rotateAngleX = ageInTicks; //full health = 1.7278761 ; 1 hp = 0.5340708

        if (entityIn instanceof EntityWolf && LetSleepingDogsLie.tickHandlerClient.getWolfInfo(((EntityWolf)entityIn)).isLying())
        {
            this.wolfTail.rotateAngleX *= 0.5796969225510672F;
        }
    }
}
