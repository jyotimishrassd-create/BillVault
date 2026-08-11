#!/bin/bash
sed -i 's/Icons.Filled.List/Icons.AutoMirrored.Filled.List/g' app/src/main/java/com/example/ui/components/AppBottomNavigation.kt
sed -i 's/Icons.Outlined.List/Icons.AutoMirrored.Outlined.List/g' app/src/main/java/com/example/ui/components/AppBottomNavigation.kt
sed -i 's/Locale("en", "IN")/Locale.Builder().setLanguage("en").setRegion("IN").build()/g' app/src/main/java/com/example/ui/screens/dashboard/DashboardScreen.kt
