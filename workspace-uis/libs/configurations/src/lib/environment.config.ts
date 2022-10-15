import { InjectionToken } from '@angular/core';

export interface IEnvironmentConfig {
  baseURL: string;
}

export const ENVIRONMENT_CONFIG = new InjectionToken("Environment Configurations");
