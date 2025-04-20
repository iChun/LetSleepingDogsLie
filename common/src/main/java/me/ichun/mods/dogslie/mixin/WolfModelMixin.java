package me.ichun.mods.dogslie.mixin;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import me.ichun.mods.dogslie.common.core.ModelHelper;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.wolf.Wolf;
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


    @Inject(method = "createMeshDefinition", at = @At("HEAD"), cancellable = true)
    private static void dogslie$createMeshDefinition(CubeDeformation cubeDeformation, CallbackInfoReturnable<MeshDefinition> cir)
    {
        //Override the original wolf model with our fixed offsets

        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
        head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, cubeDeformation).texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F), PartPose.ZERO);
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, cubeDeformation);
        root.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-1.5F, 16.0F, 7.0F));
        root.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(1.5F, 16.0F, 7.0F));
        root.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-1.5F, 16.0F, -4.0F));
        root.addOrReplaceChild("left_front_leg", var4, PartPose.offset(1.5F, 16.0F, -4.0F));
        PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, cubeDeformation), PartPose.ZERO);
        cir.setReturnValue(mesh);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/WolfRenderState;)V", at = @At("HEAD"), cancellable = true)
    private void dogslie$setupAnim(WolfRenderState renderState, CallbackInfo ci)
    {
        Wolf wolf = LetSleepingDogsLie.eventHandlerClient.wolfRendered.get();
        if(wolf == null) return;

        //largely taken from the new WolfModel setupAnim
        ((WolfModel)(Object)this).resetPose();
        float f = renderState.walkAnimationPos;
        float g = renderState.walkAnimationSpeed;
        if (renderState.isAngry) {
            this.tail.yRot = 0.0F;
        } else {
            this.tail.yRot = Mth.cos(f * 0.6662F) * 1.4F * g;
        }

        if (renderState.isSitting)
        {
            EventHandlerClient.WolfInfo info = LetSleepingDogsLie.eventHandlerClient.getWolfInfo(wolf);
            if(info.isLying())
            {
                float halfPi = ((float)Math.PI / 2F);
                float ageScale = renderState.ageScale;

                String[] poses = info.getCompatiblePoses(wolf);

                if(wolf.getName().getString().equals("iChun"))
                {
                    this.body.yRot = this.upperBody.yRot = (renderState.ageInTicks / 3.5F);
                    this.body.zRot = this.upperBody.zRot = (renderState.ageInTicks / 3.5F);

                    this.rightHindLeg.yRot = this.rightFrontLeg.yRot = (renderState.ageInTicks / 3.5F);
                    this.leftHindLeg.yRot = this.leftFrontLeg.yRot = -(renderState.ageInTicks / 3.5F);
                    this.rightHindLeg.zRot = this.rightFrontLeg.zRot = (renderState.ageInTicks / 5F);
                    this.leftHindLeg.zRot = this.leftFrontLeg.zRot = -(renderState.ageInTicks / 5F);
                }

                if(renderState.isBaby)
                {
                    this.head.y -= 1F;
                }

                ModelHelper.transformModel(poses, this.head, this.upperBody, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.body, this.rightHindLeg, this.leftHindLeg, ageScale);
            }
            else //not lying, is sitting
            {
                float h = renderState.ageScale;
                ModelPart part = this.upperBody;
                part.y += 2.0F * h;
                this.upperBody.xRot = 1.2566371F;
                this.upperBody.yRot = 0.0F;
                part = this.body;
                part.y += 4.0F * h;
                part = this.body;
                part.z -= 2.0F * h;
                this.body.xRot = ((float)Math.PI / 4F);
                part = this.tail;
                part.y += 9.0F * h;
                part = this.tail;
                part.z -= 2.0F * h;
                part = this.rightHindLeg;
                part.y += 6.7F * h;
                part = this.rightHindLeg;
                part.z -= 5.0F * h;
                this.rightHindLeg.xRot = ((float)Math.PI * 1.5F);
                part = this.leftHindLeg;
                part.y += 6.7F * h;
                part = this.leftHindLeg;
                part.z -= 5.0F * h;
                this.leftHindLeg.xRot = ((float)Math.PI * 1.5F);
                this.rightFrontLeg.xRot = 5.811947F;
                part = this.rightFrontLeg;
                part.x += 0.01F * h;
                part = this.rightFrontLeg;
                part.y += 1.0F * h;
                this.leftFrontLeg.xRot = 5.811947F;
                part = this.leftFrontLeg;
                part.x -= 0.01F * h;
                part = this.leftFrontLeg;
                part.y += 1.0F * h;

                this.upperBody.zRot = renderState.getBodyRollAngle(-0.08F);
                this.body.zRot = renderState.getBodyRollAngle(-0.16F);
                this.realTail.zRot = renderState.getBodyRollAngle(-0.2F);
            }
        }
        else //not sitting
        {
            this.rightHindLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * g;
            this.leftHindLeg.xRot = Mth.cos(f * 0.6662F + (float)Math.PI) * 1.4F * g;
            this.rightFrontLeg.xRot = Mth.cos(f * 0.6662F + (float)Math.PI) * 1.4F * g;
            this.leftFrontLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * g;

            this.upperBody.zRot = renderState.getBodyRollAngle(-0.08F);
            this.body.zRot = renderState.getBodyRollAngle(-0.16F);
            this.realTail.zRot = renderState.getBodyRollAngle(-0.2F);
        }

        this.realHead.zRot = renderState.headRollAngle + renderState.getBodyRollAngle(0.0F);
        this.head.xRot = renderState.xRot * ((float)Math.PI / 180F);
        this.head.yRot = renderState.yRot * ((float)Math.PI / 180F);
        this.tail.xRot = renderState.tailAngle;

        if (LetSleepingDogsLie.eventHandlerClient.getWolfInfo(wolf).isLying())
        {
            this.tail.xRot *= 0.5796969225510672F;
        }

        ci.cancel();
    }
}
