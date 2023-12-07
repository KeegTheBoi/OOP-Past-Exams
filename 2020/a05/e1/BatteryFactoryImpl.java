package a05.e1;

public class BatteryFactoryImpl implements BatteryFactory {


    public  class SimpleBattery implements Battery{
        protected double energy;

        public SimpleBattery(){
            this.energy = 1.0;
        }

        @Override
        public void startUse() {}
        
        @Override
        public void stopUse(double duration) {
            this.energy -= duration;
                if (this.energy < 0.0) {
                    this.energy = 0.0;
                }
        }

        @Override
        public double getEnergy() {
            return this.energy;
        }

        @Override
        public void recharge() {}


    }

    public class SecureBattery implements Battery {

        private Battery decorated;
        private boolean inUse;
        public SecureBattery(final Battery battery) {
            this.decorated = battery;
        }
        @Override
        public void startUse() {
            if(inUse || this.getEnergy() == 0.0) {
                throw new IllegalStateException();
            }
            this.inUse = true;
        }

        @Override
        public void stopUse(double duration) {
            if(!inUse) {
                throw new IllegalStateException();
            }
            this.decorated.stopUse(duration);
            this.inUse = false;    
                   
        }

        @Override
        public double getEnergy() {
            return this.decorated.getEnergy();
        }

        @Override
        public void recharge() {
            this.decorated.recharge();        
        }
        
    }

    public class RechargableBattery implements Battery {

        private Battery decorated;
        private double rechargeLevel;
        private double energy;

        public RechargableBattery(final Battery battery) {
            this.decorated = battery;
            this.energy = this.decorated.getEnergy();
            rechargeLevel = 1.0;
        }
        @Override
        public void startUse() {
            this.decorated.startUse();
        }

        @Override
        public void stopUse(double duration) {
            this.decorated.stopUse(duration);
            this.energy = this.decorated.getEnergy();
        }

        @Override
        public double getEnergy() {
            return this.energy;
        }       

        @Override
        public void recharge() {
            this.energy = rechargeLevel;
            this.rechargeLevel -= 0.01;
        }
        
    }
     

    @Override
    public Battery createSimpleBattery() {
        return new SimpleBattery(){

            @Override
            public void stopUse(double duration) {

                this.energy -= duration;
                if (this.energy < 0.0) {
                    this.energy = 0.0;
                }
            }

        };
    }
    


    @Override
    public Battery createSimpleBatteryByDrop(double energyPerDurationDrop) {
       return new SimpleBattery() {
            @Override
            public void stopUse(double duration) {
                super.stopUse(duration * energyPerDurationDrop);
            }
        };
   
    }

    @Override
    public Battery createSimpleBatteryWithExponentialDrop() {
        return new SimpleBattery() {

            @Override
            public void stopUse(double duration) {
                this.energy /= 2;
            }
            
        };
    }

    @Override
    public Battery createSecureBattery() {
        return new SecureBattery(this.createSimpleBattery());
    }

    @Override
    public Battery createRechargeableBattery() {
        return new RechargableBattery(this.createSimpleBattery());
    }

    @Override
    public Battery createSecureAndRechargeableBattery() {
        return new SecureBattery(new RechargableBattery(this.createSimpleBattery()));
    }

}
