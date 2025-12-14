// src/app/app.config.ts

import { ApplicationConfig, provideBrowserGlobalErrorListeners, importProvidersFrom } from '@angular/core'; importProvidersFrom
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms'; 




export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), 
    provideClientHydration(withEventReplay()),
   
    provideHttpClient(), 
    importProvidersFrom(ReactiveFormsModule) 
  ]
};