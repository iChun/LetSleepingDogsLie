package me.ichun.mods.dogslie.mixin;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfModel.class)
public abstract class WolfModelMixin
{
    @Shadow
    @Final
    @Mutable
    private ModelPart head;
    @Shadow
    @Final
    @Mutable
    private ModelPart realHead;
    @Shadow
    @Final
    @Mutable
    private ModelPart body;
    @Shadow
    @Final
    @Mutable
    private ModelPart rightHindLeg;
    @Shadow
    @Final
    @Mutable
    private ModelPart leftHindLeg;
    @Shadow
    @Final
    @Mutable
    private ModelPart rightFrontLeg;
    @Shadow
    @Final
    @Mutable
    private ModelPart leftFrontLeg;
    @Shadow
    @Final
    @Mutable
    private ModelPart tail;
    @Shadow
    @Final
    @Mutable
    private ModelPart realTail;
    @Shadow
    @Final
    @Mutable
    private ModelPart upperBody;


    @Inject(method = "createBodyLayer", at = @At("HEAD"), cancellable = true)
    private static void dogslie_createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir)
    {
        //Override the original wolf model with our fixed offsets

        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        PartDefinition var3 = var1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
        var3.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F).texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F), PartPose.ZERO);
        var1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        var1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
        var1.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-1.5F, 23.0F, 7.0F));
        var1.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(1.5F, 23.0F, 7.0F));
        var1.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-1.5F, 23.0F, -4.0F));
        var1.addOrReplaceChild("left_front_leg", var4, PartPose.offset(1.5F, 23.0F, -4.0F));
        PartDefinition var5 = var1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        var5.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), PartPose.ZERO);
        cir.setReturnValue(LayerDefinition.create(var0, 64, 32));
    }

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/animal/Wolf;FFF)V", at = @At("HEAD"), cancellable = true)
    private void dogslie_prepareMobModel(Wolf wolf, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci)
    {
        if (wolf.isAngry())
        {
            this.tail.yRot = 0.0F;
        }
        else
        {
            this.tail.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        //RESET POSITIONS
        this.head.setPos(-1.0F, 13.5F, -7.0F);
        this.rightHindLeg.yRot = this.leftHindLeg.yRot = this.rightFrontLeg.yRot = this.leftFrontLeg.yRot = 0F;
        this.rightHindLeg.zRot = this.leftHindLeg.zRot = this.rightFrontLeg.zRot = this.leftFrontLeg.zRot = 0F;
        this.upperBody.yRot = this.body.yRot = 0F;
        this.upperBody.zRot = this.body.zRot = 0F;
        this.realTail.zRot = 0F;
        //END RESET POSITIONS

        if (wolf.isInSittingPose()) //isInSittingPose() = isEntitySleeping()
        {
            EventHandlerClient.WolfInfo info = LetSleepingDogsLie.eventHandlerClient.getWolfInfo(wolf);
            if(info.isLying())
            {
                float halfPi = ((float)Math.PI / 2F);

                String[] poses = info.getCompatiblePoses(wolf);

                //mane flat; set the front
                this.upperBody.setPos(0.0F, 20.9F, -3.0F);
                this.upperBody.xRot = halfPi;
                this.upperBody.yRot = 0.0F;

                if(wolf.getName().getString().equals("iChun"))
                {
                    this.body.yRot = this.upperBody.yRot = ((wolf.tickCount + partialTick) / 3.5F);
                    this.body.zRot = this.upperBody.zRot = ((wolf.tickCount + partialTick) / 3.5F);

                    this.rightHindLeg.yRot = this.rightFrontLeg.yRot = ((wolf.tickCount + partialTick) / 3.5F);
                    this.leftHindLeg.yRot = this.leftFrontLeg.yRot = -((wolf.tickCount + partialTick) / 3.5F);
                    this.rightHindLeg.zRot = this.rightFrontLeg.zRot = ((wolf.tickCount + partialTick) / 5F);
                    this.leftHindLeg.zRot = this.leftFrontLeg.zRot = -((wolf.tickCount + partialTick) / 5F);
                }

                switch(poses[0]) //front
                {
                    case "forelegSideL" ->
                    {
                        this.upperBody.setPos(0.50F, 20.9F, -3.0F);
                        this.upperBody.xRot = halfPi;
                        this.upperBody.zRot = -0.956091F;

                        this.head.setPos(-1.0F, 20.50F, -7.0F);

                        this.rightFrontLeg.setPos(-1.5F, 23.0F, -4.0F);
                        this.rightFrontLeg.xRot = -0.087266F;
                        this.rightFrontLeg.zRot = -halfPi;

                        this.leftFrontLeg.setPos(1.5F, 21.0F, -3.0F);
                        this.leftFrontLeg.xRot = 0.10472F;
                        this.leftFrontLeg.zRot = -1.320342F;
                    }
                    case "forelegSideR" ->
                    {
                        this.upperBody.setPos(-0.50F, 20.9F, -3.0F);
                        this.upperBody.xRot = halfPi;
                        this.upperBody.zRot = 0.956091F;

                        this.head.setPos(-1.0F, 20.50F, -7.0F);

                        this.rightFrontLeg.setPos(-1.5F, 21.0F, -3.0F);
                        this.rightFrontLeg.xRot = 0.10472F;
                        this.rightFrontLeg.zRot = 1.320342F;

                        this.leftFrontLeg.setPos(1.5F, 23.0F, -4.0F);
                        this.leftFrontLeg.xRot = -0.087266F;
                        this.leftFrontLeg.zRot = halfPi;
                    }
                    case "forelegStraight" ->
                    {
                        this.head.setPos(-1.0F, 19.0F, -7.0F);

                        this.rightFrontLeg.setPos(-1.5F, 23.0F, -4.0F);
                        this.rightFrontLeg.xRot = -halfPi;

                        this.leftFrontLeg.setPos(1.5F, 23.0F, -4.0F);
                        this.leftFrontLeg.xRot = -halfPi;
                    }
                    case "forelegSprawled" ->
                    {
                        this.head.setPos(-1.0F, 20.4F, -7.0F);

                        this.rightFrontLeg.setPos(-1.5F, 23.0F, -4.0F);
                        this.rightFrontLeg.xRot = -halfPi;
                        this.rightFrontLeg.yRot = 0.63739424F;

                        this.leftFrontLeg.setPos(1.5F, 23.0F, -4.0F);
                        this.leftFrontLeg.xRot = -halfPi;
                        this.leftFrontLeg.yRot = -0.63739424F;
                    }
                    case "forelegSprawledBack" ->
                    {
                        this.head.setPos(-1.0F, 21F, -7.0F);

                        this.rightFrontLeg.setPos(-1.5F, 23.0F, -4.0F);
                        this.rightFrontLeg.xRot = halfPi;
                        this.rightFrontLeg.yRot = -0.63739424F;

                        this.leftFrontLeg.setPos(1.5F, 23.0F, -4.0F);
                        this.leftFrontLeg.xRot = halfPi;
                        this.leftFrontLeg.yRot = 0.63739424F;
                    }
                    case "forelegSkewedL" ->
                    {
                        this.head.setPos(-1.0F, 20.0F, -7.0F);

                        this.rightFrontLeg.setPos(-1.5F, 23.0F, -4.0F);
                        this.rightFrontLeg.xRot = -halfPi;
                        this.rightFrontLeg.yRot = -0.436332F;

                        this.leftFrontLeg.setPos(1.5F, 23.0F, -4.0F);
                        this.leftFrontLeg.xRot = -halfPi;
                        this.leftFrontLeg.yRot = -0.349066F;
                    }
                    case "forelegSkewedR" ->
                    {
                        this.head.setPos(-1.0F, 20.0F, -7.0F);

                        this.rightFrontLeg.setPos(-1.5F, 23.0F, -4.0F);
                        this.rightFrontLeg.xRot = -halfPi;
                        this.rightFrontLeg.yRot = 0.349066F;

                        this.leftFrontLeg.setPos(1.5F, 23.0F, -4.0F);
                        this.leftFrontLeg.xRot = -halfPi;
                        this.leftFrontLeg.yRot = 0.436332F;
                    }
                    default -> {}
                }

                //body flat; set the back
                this.body.setPos(0.0F, 20.9F, 2.0F);
                this.body.xRot = halfPi;

                this.tail.setPos(-1.0F, 19.0F, 8.0F);

                switch(poses[1])
                {
                    case "hindlegSideL" ->
                    {
                        this.body.setPos(0.0F, 20.9F, 2.0F);
                        this.body.xRot = halfPi;
                        this.body.zRot = -0.610865F;

                        this.tail.setPos(-2.0F, 19.8F, 8.0F);
                        this.realTail.zRot = -0.610865F;

                        this.rightHindLeg.setPos(-0.2F, 23.5F, 6.5F);
                        this.rightHindLeg.xRot = -halfPi;
                        this.rightHindLeg.yRot = -0.956091F;

                        this.leftHindLeg.setPos(1.5F, 23.0F, 7.0F);
                        this.leftHindLeg.xRot = -halfPi;
                        this.leftHindLeg.yRot = -1.365895F;
                    }
                    case "hindlegSideR" ->
                    {
                        this.body.setPos(0.0F, 20.9F, 2.0F);
                        this.body.xRot = halfPi;
                        this.body.zRot = 0.610865F;

                        this.tail.setPos(0.0F, 19.8F, 8.0F);
                        this.realTail.zRot = 0.610865F;

                        this.rightHindLeg.setPos(-1.5F, 23.0F, 7.0F);
                        this.rightHindLeg.xRot = -halfPi;
                        this.rightHindLeg.yRot = 1.365895F;

                        this.leftHindLeg.setPos(0.2F, 23.5F, 6.5F);
                        this.leftHindLeg.xRot = -halfPi;
                        this.leftHindLeg.yRot = 0.956091F;
                    }
                    case "hindlegStraight" ->
                    {
                        this.rightHindLeg.setPos(-1.5F, 23.0F, 7.0F);
                        this.rightHindLeg.xRot = -halfPi;

                        this.leftHindLeg.setPos(1.5F, 23.0F, 7.0F);
                        this.leftHindLeg.xRot = -halfPi;
                    }
                    case "hindlegStraightBack" ->
                    {
                        this.rightHindLeg.setPos(-1.5F, 23.0F, 7.0F);
                        this.rightHindLeg.xRot = halfPi;

                        this.leftHindLeg.setPos(1.5F, 23.0F, 7.0F);
                        this.leftHindLeg.xRot = halfPi;
                    }
                    case "hindlegSprawled" ->
                    {
                        this.rightHindLeg.setPos(-1.5F, 23.0F, 7.0F);
                        this.rightHindLeg.xRot = -halfPi;
                        this.rightHindLeg.yRot = 0.523599F;

                        this.leftHindLeg.setPos(1.5F, 23.0F, 7.0F);
                        this.leftHindLeg.xRot = -halfPi;
                        this.leftHindLeg.yRot = -0.523599F;
                    }
                    case "hindlegSprawledBack" ->
                    {
                        this.rightHindLeg.setPos(-1.5F, 23.0F, 7.0F);
                        this.rightHindLeg.xRot = halfPi;
                        this.rightHindLeg.yRot = -0.523599F;

                        this.leftHindLeg.setPos(1.5F, 23.0F, 7.0F);
                        this.leftHindLeg.xRot = halfPi;
                        this.leftHindLeg.yRot = 0.523599F;
                    }
                }

                if(((WolfModel)(Object)this).young)
                {
                    this.head.y -= (this.head.y - 13.5F) * 0.5F;
                }
            }
            else
            {
                this.upperBody.setPos(0.0F, 16.0F, -3.0F);
                this.upperBody.xRot = ((float)Math.PI * 2F / 5F);
                this.upperBody.yRot = 0.0F;
                this.body.setPos(0.0F, 18.0F, 0.0F);
                this.body.xRot = ((float)Math.PI / 4F);
                this.tail.setPos(-1.0F, 21.0F, 6.0F);
                this.rightHindLeg.setPos(-1.5F, 22.0F, 2.0F);
                this.rightHindLeg.xRot = ((float)Math.PI * 1.5F);
                this.leftHindLeg.setPos(1.5F, 22.0F, 2.0F);
                this.leftHindLeg.xRot = ((float)Math.PI * 1.5F);
                this.rightFrontLeg.xRot = 5.811947F;
                this.rightFrontLeg.setPos(-1.49F, 17.0F, -4.0F);
                this.leftFrontLeg.xRot = 5.811947F;
                this.leftFrontLeg.setPos(1.51F, 17.0F, -4.0F);


                this.upperBody.zRot = wolf.getBodyRollAngle(partialTick, -0.08F);
                this.body.zRot = wolf.getBodyRollAngle(partialTick, -0.16F);
                this.realTail.zRot = wolf.getBodyRollAngle(partialTick, -0.2F);
            }
        }
        else
        {
            this.body.setPos(0.0F, 14.0F, 2.0F);
            this.body.xRot = ((float)Math.PI / 2F);
            this.upperBody.setPos(0.0F, 14.0F, -3.0F);
            this.upperBody.xRot = this.body.xRot;
            this.tail.setPos(-1.0F, 12.0F, 8.0F);
            this.rightHindLeg.setPos(-1.5F, 16.0F, 7.0F);
            this.leftHindLeg.setPos(1.5F, 16.0F, 7.0F);
            this.rightFrontLeg.setPos(-1.5F, 16.0F, -4.0F);
            this.leftFrontLeg.setPos(1.5F, 16.0F, -4.0F);
            this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;


            this.upperBody.zRot = wolf.getBodyRollAngle(partialTick, -0.08F);
            this.body.zRot = wolf.getBodyRollAngle(partialTick, -0.16F);
            this.realTail.zRot = wolf.getBodyRollAngle(partialTick, -0.2F);
        }

        this.realHead.zRot = wolf.getHeadRollAngle(partialTick) + wolf.getBodyRollAngle(partialTick, 0.0F);

        ci.cancel();
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Wolf;FFFFF)V", at = @At("HEAD"), cancellable = true)
    private void dogslie_setupAnim(Wolf entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.tail.xRot = ageInTicks; //full health = 1.7278761 ; 1 hp = 0.5340708

        if (LetSleepingDogsLie.eventHandlerClient.getWolfInfo(entity).isLying())
        {
            this.tail.xRot *= 0.5796969225510672F;
        }

        ci.cancel();
    }
}
